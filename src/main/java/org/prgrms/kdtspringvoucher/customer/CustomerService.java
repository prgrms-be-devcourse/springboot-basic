package org.prgrms.kdtspringvoucher.customer;

import org.prgrms.kdtspringvoucher.customer.entity.Customer;
import org.prgrms.kdtspringvoucher.customer.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final BlackCustomerRepository blackCustomerRepository;

    public CustomerService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<Customer> getAllCustomers() {
        return blackCustomerRepository.listAll();
    }
}
