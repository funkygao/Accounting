package reengineering.ddd.accounting.mybatis.associations;

import org.springframework.stereotype.Component;
import reengineering.ddd.accounting.model.Customer;
import reengineering.ddd.accounting.mybatis.Dao;

import javax.inject.Inject;
import java.util.Optional;


@Component
public class CustomerRepository implements reengineering.ddd.accounting.model.CustomerRepository {
    private Dao mapper;

    @Inject
    public CustomerRepository(Dao mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(mapper.findCustomerById(id));
    }
}
