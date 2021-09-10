package org.prgrms.kdt.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.model.CustomerType;
import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(UUID customerId) {
        return customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new RuntimeException("Can not find customer %s".formatted(customerId)));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomer();
    }

    public List<Customer> getAllBlackCustomers() {
        return customerRepository.findByCustomerType(CustomerType.BLACK);
    }

}
