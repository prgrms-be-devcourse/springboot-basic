package org.weekly.weekly.customer.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.dto.response.CustomersResponse;
import org.weekly.weekly.customer.exception.CustomerException;
import org.weekly.weekly.customer.repository.CustomerRepository;
import org.weekly.weekly.util.ExceptionMsg;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerCreationRequest creationRequest) {
        validateCustomerNotExist(creationRequest.getEmail());

        Customer customer = creationRequest.toCustomer();
        customerRepository.insert(customer);
        return CustomerResponse.of(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerUpdateRequest updateRequest) {
        deleteCustomer(updateRequest.email());
    }

    public void deleteCustomer(String customerEmail) {
        customerRepository.deleteByEmail(customerEmail);
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findDetailCustomer(CustomerUpdateRequest updateRequest) {
        return findDetailCustomer(updateRequest.email());
    }

    public CustomerResponse findDetailCustomer(String customerEmail) {
        Customer customer = validateCustomerExistAndGet(customerEmail);
        return CustomerResponse.of(customer);
    }


    public CustomersResponse findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return new CustomersResponse(customers);
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerUpdateRequest updateRequest) {
        validateCustomerNotExist(updateRequest.newEmail());

        Customer customer = validateCustomerExistAndGet(updateRequest.email());

        Customer updateCustomer = customerRepository.update(customer, updateRequest.newEmail());
        return CustomerResponse.of(updateCustomer);
    }

    private void validateCustomerNotExist(String email) {
        Optional<Customer> findCustomer = customerRepository.findByEmail(email);
        if (findCustomer.isPresent()) {
            throw new CustomerException(ExceptionMsg.SQL_EXIST);
        }
    }

    private Customer validateCustomerExistAndGet(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isEmpty()) {
            throw new CustomerException(ExceptionMsg.SQL_ERROR);
        }
        return customer.get();
    }
}
