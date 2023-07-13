package com.programmers.springmission.customer.application;

import com.programmers.springmission.customer.domain.Customer;
import com.programmers.springmission.customer.domain.Wallet;
import com.programmers.springmission.customer.presentation.request.CustomerCreateRequest;
import com.programmers.springmission.customer.presentation.request.CustomerUpdateRequest;
import com.programmers.springmission.customer.presentation.response.CustomerResponse;
import com.programmers.springmission.customer.presentation.response.WalletResponse;
import com.programmers.springmission.customer.repository.CustomerRepository;
import com.programmers.springmission.global.exception.ErrorMessage;
import com.programmers.springmission.global.exception.InvalidInputException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {
        duplicatedCustomerEmail(customerCreateRequest.getEmail());
        Customer customer = new Customer(customerCreateRequest.getName(), customerCreateRequest.getEmail());

        customerRepository.save(customer);
        return new CustomerResponse(customer);
    }

    public CustomerResponse findByIdCustomer(UUID customerId) {
        Customer customer = validateCustomerExistById(customerId);

        return new CustomerResponse(customer);
    }

    public CustomerResponse findByEmailCustomer(String email) {
        Customer customer = validateCustomerExistByEmail(email);

        return new CustomerResponse(customer);
    }

    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream()
                .map(CustomerResponse::new)
                .toList();
    }

    @Transactional
    public CustomerResponse updateName(UUID customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = validateCustomerExistById(customerId);
        customer.updateName(customerUpdateRequest.getName());

        customerRepository.updateName(customer);
        return new CustomerResponse(customer);
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = validateCustomerExistById(customerId);

        customerRepository.deleteById(customer.getCustomerId());
    }

    @Transactional
    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }

    public List<WalletResponse> findWallet(UUID inputCustomerId) {
        Customer customer = validateCustomerExistById(inputCustomerId);

        List<Wallet> walletList = customerRepository.findWallet(customer.getCustomerId());
        return walletList.stream()
                .map(WalletResponse::new)
                .toList();
    }

    private Customer validateCustomerExistById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.NOT_EXIST_CUSTOMER));
    }

    private Customer validateCustomerExistByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidInputException(ErrorMessage.NOT_EXIST_CUSTOMER));
    }

    private void duplicatedCustomerEmail(String email) {
        customerRepository.findByEmail(email).ifPresent(customer -> {
            throw new InvalidInputException(ErrorMessage.DUPLICATE_CUSTOMER_EMAIL);
        });
    }
}
