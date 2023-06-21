package reengineering.ddd.accounting.model;

import reengineering.ddd.archtype.Many;

public class Foo {

    void demo() {
        Customer customer = new Customer();
        // 通过关联对象的窄接口获取
        Account account = customer.accounts().findByIdentity("a").get();
        customer.addSourceEvidence();
        Many<Transaction> transactions = account.transactions().findAll();
        account.addTransactionsWithSourceEvidence();
        Transaction transaction = new Transaction();
        transaction.sourceEvidence().transactions().findAll();


    }
}
