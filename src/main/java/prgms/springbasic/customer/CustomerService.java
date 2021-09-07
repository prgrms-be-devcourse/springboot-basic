package prgms.springbasic.customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(String name);
    Customer findByName(String name);
    List<Customer> getCustomerList();
}
