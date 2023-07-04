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
import java.util.UUID;

import static org.promgrammers.springbootbasic.exception.ErrorCode.DUPLICATED_USERNAME;
import static org.promgrammers.springbootbasic.exception.ErrorCode.INVALID_USERNAME_MESSAGE;
import static org.promgrammers.springbootbasic.exception.ErrorCode.NOT_FOUND_CUSTOMER;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9가-힣]+$";


    public CustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        validateUsername(customerRequest.username());

        Customer customer = new Customer(UUID.randomUUID(), customerRequest.username());
        customerRepository.save(customer);
        return new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));
        return new CustomerResponse(customerId, customer.getUsername(), customer.getCustomerType());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));
        return new CustomerResponse(customer.getCustomerId(), username, customer.getCustomerType());
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

        validateUsername(updateCustomerRequest.username());

        customer.updateUsername(updateCustomerRequest.username());
        customer.updateCustomerType(updateCustomerRequest.customerType());
        customerRepository.update(customer);

        CustomerResponse customerResponse = new CustomerResponse(updateCustomerRequest.customerId(), updateCustomerRequest.username(), updateCustomerRequest.customerType());
        return customerResponse;
    }

    @Transactional
    public void deleteById(UUID customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new BusinessException(NOT_FOUND_CUSTOMER));

        customerRepository.deleteById(customerId);
    }

    private void validateUsername(String username) {

        if (!username.matches(USERNAME_REGEX)) {
            throw new BusinessException(INVALID_USERNAME_MESSAGE);
        }

        if (customerRepository.findByUsername(username).isPresent()) {
            throw new BusinessException(DUPLICATED_USERNAME);
        }
    }

}
