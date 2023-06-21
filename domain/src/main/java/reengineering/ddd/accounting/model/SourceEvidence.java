package reengineering.ddd.accounting.model;

import reengineering.ddd.accounting.description.SourceEvidenceDescription;
import reengineering.ddd.accounting.description.TransactionDescription;
import reengineering.ddd.archtype.HasMany;
import reengineering.ddd.archtype.Entity;

import java.util.List;
import java.util.Map;

/**
 * 业务单据的原始凭证, business documents.
 * @param <Description>
 */
public interface SourceEvidence<Description extends SourceEvidenceDescription> extends Entity<String, Description> {

    /**
     * 一份业务单据的原始凭证，会被记录到多份记账流水里.
     */
    interface Transactions extends HasMany<String, Transaction> {
    }

    HasMany<String, Transaction> transactions();

    // key is account.identity
    Map<String, List<TransactionDescription>> toTransactions();
}
