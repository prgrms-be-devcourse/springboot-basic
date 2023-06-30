package org.promgrammers.springbootbasic.domain.customer.service;

import org.promgrammers.springbootbasic.domain.customer.dto.request.CreateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.request.UpdateCustomerRequest;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.CustomerRepository;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.promgrammers.springbootbasic.exception.ErrorCode.DUPLICATED_USERNAME;
import static org.promgrammers.springbootbasic.exception.ErrorCode.NOT_FOUND_CUSTOMER;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        duplicateUsernameCheck(customerRequest.username());
        Customer customer = new Customer(UUID.randomUUID(), customerRequest.username());
        customerRepository.save(customer);
        return new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType());
    }

    @Transactional(readOnly = true)
    public Optional<CustomerResponse> findCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType()))
                .or(() -> {
                    throw new BusinessException(NOT_FOUND_CUSTOMER);
                });
    }

    @Transactional(readOnly = true)
    public Optional<CustomerResponse> findCustomerByUsername(String username) {
        return customerRepository.findByUsername(username)
                .map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType()))
                .or(() -> {
                    throw new BusinessException(NOT_FOUND_CUSTOMER);
                });
    }

    @Transactional(readOnly = true)
    public CustomersResponse findAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        if (customerList == null || customerList.isEmpty()) {
            throw new BusinessException(NOT_FOUND_CUSTOMER);
        }

        List<CustomerResponse> customerResponseList = customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType()))
                .toList();

        return new CustomersResponse(customerResponseList);
    }

    @Transactional
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @Transactional
    public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(updateCustomerRequest.customerId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        customer.updateUsername(updateCustomerRequest.username());
        customer.updateCustomerType(updateCustomerRequest.customerType());
        customerRepository.update(customer);

        CustomerResponse customerResponse = new CustomerResponse(updateCustomerRequest.customerId(), updateCustomerRequest.username(), updateCustomerRequest.customerType());
        return customerResponse;
    }

    private void duplicateUsernameCheck(String username) {
        if (customerRepository.findByUsername(username).isPresent()) {
            throw new BusinessException(DUPLICATED_USERNAME);
        }
    }
}
