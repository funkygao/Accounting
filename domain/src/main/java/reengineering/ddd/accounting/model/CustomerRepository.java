package reengineering.ddd.accounting.model;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String id);
}
