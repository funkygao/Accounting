package reengineering.ddd.accounting.api;

import reengineering.ddd.accounting.model.CustomerRepository;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/customers")
public class CustomersApi {
    private CustomerRepository customerRepository;

    @Inject
    public CustomersApi(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Path("{id}")
    public CustomerApi findById(@PathParam("id") String id) {
        return customerRepository.findById(id).map(CustomerApi::new).orElse(null);
    }
}
