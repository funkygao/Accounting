package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.SalesSettlementDescription;
import reengineering.ddd.accounting.description.TransactionDescription;
import reengineering.ddd.archtype.HasMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 销售结算单：一种{@link SourceEvidence}.
 *
 * <p>此外，还有其他种类的业务单据原始凭证.</p>
 */
public class SalesSettlement implements SourceEvidence<SalesSettlementDescription> {
    private String identity;
    private SalesSettlementDescription description;

    private HasMany<String, Transaction> transactions;

    public SalesSettlement() {
    }

    public SalesSettlement(String identity, SalesSettlementDescription description, HasMany<String, Transaction> transactions) {
        this.identity = identity;
        this.description = description;
        this.transactions = transactions;
    }

    @Override
    public Map<String, List<TransactionDescription>> toTransactions() {
        return Map.of(getDescription().getAccount().id(),
                getDescription().getDetails().stream().map(detail -> new TransactionDescription(detail.getAmount(), LocalDateTime.now())).toList());
    }
}
