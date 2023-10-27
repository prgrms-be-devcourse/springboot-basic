package team.marco.vouchermanagementsystem.service;

import org.springframework.stereotype.Service;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.repository.custromer.CustomerRepository;

import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomer(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow();
    }
}
