package prgms.springbasic.customer;

import java.util.List;

public interface CustomerRepository {
    Customer save(Customer customer);

    Customer findByName(String name);

    List<Customer> getCustomerList();
}
