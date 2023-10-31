package com.zerozae.voucher.service.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.repository.customer.CustomerRepository;
import com.zerozae.voucher.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class CustomerService {

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "회원이 존재하지 않습니다.";
    private static final String ALREADY_EXIST_CUSTOMER_MESSAGE = "이미 존재하는 회원입니다.";

    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;

    public CustomerService(CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    public CustomerResponse createCustomer(CustomerCreateRequest customerRequest) {
        try{
            validateDuplicateCustomer(customerRequest);
            Customer customer = customerRequest.to(UUID.randomUUID());
            customerRepository.save(customer);
            return CustomerResponse.toDto(customer);
        }catch (ExceptionMessage e){
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerResponse::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAllBlacklistCustomers() {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerType().equals(CustomerType.BLACKLIST))
                .map(CustomerResponse::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> ExceptionMessage.error(CUSTOMER_NOT_FOUND_MESSAGE));
        return CustomerResponse.toDto(customer);
    }

    public void deleteById(UUID customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> ExceptionMessage.error(CUSTOMER_NOT_FOUND_MESSAGE));
        customerRepository.delete(customerId);
        walletRepository.deleteByCustomerId(customerId);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
        walletRepository.deleteAll();
    }

    public void update(UUID customerId, CustomerUpdateRequest customerRequest) {
        customerRepository.findById(customerId).orElseThrow(() -> ExceptionMessage.error(CUSTOMER_NOT_FOUND_MESSAGE));
        customerRepository.update(customerId, customerRequest);
    }

    private void validateDuplicateCustomer(CustomerCreateRequest customerRequest) {
        Optional<Customer> findCustomer = customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getCustomerName().equals(customerRequest.getCustomerName()))
                .findAny();

        if(findCustomer.isPresent()) {
            throw ExceptionMessage.error(ALREADY_EXIST_CUSTOMER_MESSAGE);
        }
    }
}
