package org.prgrms.kdt.model.customer.service;

import org.prgrms.kdt.exception.NotPresentInRepositoryException;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.repository.CustomerRepository;
import org.prgrms.kdt.util.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.getAllStoredCustomer();
    }

    public List<Customer> getAllBlacklist() {
        return customerRepository.getAllStoredCustomer().stream()
                .filter(Customer::isBlacklist)
                .toList();
    }

    public Customer findCustomerById(String customerId) {
        return customerRepository.findById(ConvertUtil.toUUID(customerId))
                .orElseThrow(() -> new NotPresentInRepositoryException("입력된 customer ID가 존재하지 않습니다."));
    }
}
