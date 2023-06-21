package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.CustomerDescription;
import reengineering.ddd.accounting.description.SourceEvidenceDescription;
import reengineering.ddd.accounting.description.TransactionDescription;
import reengineering.ddd.archtype.Entity;
import reengineering.ddd.archtype.HasMany;

import java.util.List;
import java.util.Map;

/**
 * 客户.
 */
public class Customer implements Entity<String, CustomerDescription> {
    private String identity;
    private CustomerDescription description;

    /**
     * 一个客户会发生多个业务单据原始凭证.
     */
    private SourceEvidences sourceEvidences;

    /**
     * 一个客户有多个账户.
     */
    private Accounts accounts;

    public Customer(String identity, CustomerDescription description,
                    SourceEvidences sourceEvidences, Accounts accounts) {
        this.identity = identity;
        this.description = description;
        this.sourceEvidences = sourceEvidences;
        this.accounts = accounts;
    }

    public HasMany<String, Account> accounts() {
        return accounts;
    }

    public HasMany<String, SourceEvidence<?>> sourceEvidences() {
        return sourceEvidences;
    }

    // TODO 核心逻辑：插入(原始凭证，记账流水)、更改账户余额 [accounts, transactions, (source_evidences, sales_settlements, sales_settlement_details)]
    public SourceEvidence<?> addSourceEvidence(SourceEvidenceDescription description) {
        // insert (source_evidences, sales_settlements, sales_settlement_details)
        SourceEvidence<?> evidence = sourceEvidences.add(description);

        Map<String, List<TransactionDescription>> transactions = evidence.toTransactions();
        for (String accountId : transactions.keySet()) {
            // 通过关联对象找到账户实体，TODO 遍历里查找每一个Account对象
            Account account = accounts.findByIdentity(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
            // insert transactions
            AccountChange accountChange = account.addTransactionsWithSourceEvidence(evidence, transactions.get(accountId));
            // update accounts
            accounts.update(account, accountChange);
        }
        return evidence;
    }

    public interface SourceEvidences extends HasMany<String, SourceEvidence<?>> {
        SourceEvidence<?> add(SourceEvidenceDescription description);
    }

    public interface Accounts extends HasMany<String, Account> {
        void update(Account account, Account.AccountChange change);
    }
}
