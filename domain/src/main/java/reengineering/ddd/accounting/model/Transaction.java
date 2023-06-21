package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.TransactionDescription;
import reengineering.ddd.archtype.Entity;
import reengineering.ddd.archtype.HasOne;

/**
 * 记账流水.
 */
public class Transaction implements Entity<String, TransactionDescription> {
    private String identity;
    private TransactionDescription description;

    /**
     * 一个记账流水对应一个原始凭证.
     */
    private HasOne<SourceEvidence<?>> sourceEvidence;

    /**
     * 一个记账流水，被记录到某一个账户下：例如，现金账户，信用账户，在途账户.
     */
    private HasOne<Account> account;

    private Transaction() {
    }

    public Transaction(String identity, TransactionDescription description,
                       HasOne<Account> account, HasOne<SourceEvidence<?>> sourceEvidence) {
        this.identity = identity;
        this.description = description;
        this.account = account;
        this.sourceEvidence = sourceEvidence;
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @Override
    public TransactionDescription getDescription() {
        return description;
    }

    public SourceEvidence sourceEvidence() {
        return sourceEvidence.get();
    }

    public Account account() {
        return account.get();
    }
}
