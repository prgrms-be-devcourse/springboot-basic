package com.prgrms.springbootbasic.service.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public void createCustomers(List<Customer> customers) {

    }

    @Override
    public Customer createCustomer(String email, String name) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        return Optional.empty();
    }
}
