package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.AccountDescription;
import reengineering.ddd.accounting.description.TransactionDescription;
import reengineering.ddd.accounting.description.basic.Amount;
import reengineering.ddd.archtype.Entity;
import reengineering.ddd.archtype.HasMany;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户: cash account/credit account/transit account/etc.
 */
public class Account implements Entity<String, AccountDescription> {
    private String identity;
    private AccountDescription description;

    /**
     * 一个账户下记录多份记账流水.
     *
     * 由于引入关联对象，Account自身就可以操作数据库.
     */
    private Transactions transactions;

    public Account(String identity, AccountDescription description, Transactions transactions) {
        this.identity = identity;
        this.description = description;
        this.transactions = transactions;
    }

    // TODO 把宽接口(有add方法)变成窄接口
    public HasMany<String, Transaction> transactions() {
        return transactions;
    }

    // 核心逻辑：把所有记账流水记录到本账户下 [transactions] TODO 不关内存，会插库
    public AccountChange add(SourceEvidence<?> evidence, List<TransactionDescription> descriptions) {
        BigDecimal total = BigDecimal.ZERO;
        for (TransactionDescription transactionDescription : descriptions) {
            // insert transactions
            Transaction transaction = transactions.add(this, evidence, transactionDescription);

            total = total.add(transaction.getDescription().amount());
        }
        Amount changeTotal = Amount.sum(total);
        description = new AccountDescription(Amount.sum(description.current(), changeTotal));
        return new AccountChange(changeTotal);
    }

    public interface Transactions extends HasMany<String, Transaction> {
        Transaction add(Account account, SourceEvidence<?> evidence, TransactionDescription description);
    }

    public record AccountChange(Amount total) {
    }
}
