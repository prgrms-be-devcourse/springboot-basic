package org.prgrms.orderApp.customer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public List<Customer> getAllBlackLIstCustomers();
    public void createCustomer(List<Customer> customers);
}
