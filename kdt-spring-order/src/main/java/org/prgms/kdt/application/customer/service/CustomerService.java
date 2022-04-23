package org.prgms.kdt.application.customer.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer join(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        validateDuplicateCustomer(customer);
        return customerRepository.insert(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer updateCustomer(Customer customer) {
        validateDuplicateCustomer(customer);
        return customerRepository.update(customer);
    }

    public void deleteCustomerById(UUID customerId) {
        customerRepository.delete(customerId);
    }

    public List<Customer> findBlacklist () {
        return customerRepository.getBlacklist();
    }

    private void validateDuplicateCustomer(Customer customer) {
        Optional<Customer> findCustomer = customerRepository.findByName(customer.getName());
        if (findCustomer.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
    }
}
