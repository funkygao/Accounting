package reengineering.ddd.accounting.mybatis.associations;

import reengineering.ddd.accounting.description.SourceEvidenceDescription;
import reengineering.ddd.accounting.model.Customer;
import reengineering.ddd.accounting.model.SourceEvidence;
import reengineering.ddd.accounting.mybatis.Dao;
import reengineering.ddd.mybatis.database.EntityList;
import reengineering.ddd.mybatis.support.IdHolder;

import javax.inject.Inject;
import java.util.List;

public class CustomerSourceEvidences extends EntityList<String, SourceEvidence<?>> implements Customer.SourceEvidences {
    private String customerId;

    @Inject
    private Dao dao;

    @Override
    protected List<SourceEvidence<?>> findEntities(int from, int to) {
        return dao.findSourceEvidencesByCustomerId(customerId, from, to - from);
    }

    @Override
    protected SourceEvidence<?> findEntity(String id) {
        return dao.findSourceEvidenceByCustomerAndId(customerId, id);
    }

    @Override
    public SourceEvidence<?> add(SourceEvidenceDescription description) {
        IdHolder holder = new IdHolder();
        // insert into source_evidences(customer_id, `type`) values(customerId, 'sales-settlement')
        dao.insertSourceEvidence(holder, customerId, description);

        // insert(sales_settlements)
        // insert(sales_settlement_details)
        dao.insertSourceEvidenceDescription(holder.id(), description);

        return dao.findSourceEvidenceByCustomerAndId(customerId, holder.id());
    }

    @Override
    public int size() {
        return dao.countSourceEvidencesByCustomer(customerId);
    }
}
