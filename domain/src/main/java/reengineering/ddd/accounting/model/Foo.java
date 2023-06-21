package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.SalesSettlementDescription;
import reengineering.ddd.accounting.description.SourceEvidenceDescription;

public class Foo {
    private Customer customer;
    private CustomerRepository repository;

    void demo() {
        // Remember, you have to provide a root association: CustomerRepository in this case
        customer = repository.findById("1").get();

        SourceEvidenceDescription description = new SalesSettlementDescription();
        // 这在之前，就会用 DomainService搞了，因为跨聚合了: SourceEvidence/Transaction/Account
        customer.addSourceEvidence(description);

        // TODO 核心：connected object graph VS disconnected aggregates
        // TODO 相当于把Repository定义到Entity里面了，不同的是repo取自己，关联对象取别人
        // 通过关联对象的【窄接口】获取其他聚合根
        Account account = customer.accounts().findByIdentity("a").get();
        customer.accounts().findAll();
        customer.sourceEvidences().findAll();
    }
}
