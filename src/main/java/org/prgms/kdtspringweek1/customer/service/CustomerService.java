package org.prgms.kdtspringweek1.customer.service;

import org.prgms.kdtspringweek1.customer.service.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<FindCustomerResponseDto> searchAllBlackCustomers() {
        return customerRepository.findAllBlackCustomer().stream()
                .map(FindCustomerResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<FindCustomerResponseDto> searchAllCustomers() {
        return customerRepository.findAllCustomers().stream()
                .map(FindCustomerResponseDto::new)
                .collect(Collectors.toList());
    }

    public Optional<FindCustomerResponseDto> searchCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).map(FindCustomerResponseDto::new);
    }

    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

}
