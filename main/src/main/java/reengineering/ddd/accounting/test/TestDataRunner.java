package reengineering.ddd.accounting.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reengineering.ddd.accounting.description.SalesSettlementDescription;
import reengineering.ddd.accounting.description.basic.Amount;
import reengineering.ddd.accounting.description.basic.Ref;
import reengineering.ddd.accounting.model.Customer;
import reengineering.ddd.accounting.model.CustomerRepository;

import javax.inject.Inject;
import java.util.Random;

@Component
public class TestDataRunner implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(TestDataRunner.class);


    private TestDataMapper mapper;
    private CustomerRepository customerRepository;

    @Inject
    public TestDataRunner(TestDataMapper mapper, CustomerRepository customerRepository) {
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Insert test data");

        String customerId = "1";
        String accountId = "1";

        mapper.insertCustomer(customerId, "John Smith", "john.smith@email.com");
        mapper.insertAccount(accountId, customerId, 100.00, "CNY");

        Customer customer = customerRepository.findById(customerId).get();

        for (var evidence = 0; evidence < 1000; evidence++) {
            var description = new SalesSettlementDescription(new Ref<>("ORD-" + new Random().nextInt()),
                    Amount.cny("500.00"), new Ref<>(accountId),
                    new SalesSettlementDescription.Detail(Amount.cny("100.00")),
                    new SalesSettlementDescription.Detail(Amount.cny("100.00")),
                    new SalesSettlementDescription.Detail(Amount.cny("100.00")),
                    new SalesSettlementDescription.Detail(Amount.cny("100.00")),
                    new SalesSettlementDescription.Detail(Amount.cny("100.00"))
            );

            customer.addSourceEvidence(description);
        }
    }
}
