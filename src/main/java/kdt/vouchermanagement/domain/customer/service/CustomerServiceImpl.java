package kdt.vouchermanagement.domain.customer.service;

import kdt.vouchermanagement.domain.customer.domain.Customer;
import kdt.vouchermanagement.domain.customer.dto.CustomerResponse;
import kdt.vouchermanagement.domain.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void saveCustomer(Customer customer) {
        if(customer == null) {
            throw new IllegalStateException();
        }

        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return Collections.unmodifiableList(CustomerResponse.listOf(customerRepository.findAll()));
    }

    @Override
    public void deleteById(long id) {
        customerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        customerRepository.deleteById(id);
    }
}
