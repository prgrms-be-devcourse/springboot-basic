package com.programmers.customer.service;

import com.programmers.customer.Customer;
import com.programmers.customer.dto.CustomerDto;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.programmers.message.ErrorMessage.*;

@Service
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public CustomerDto join(String name, String email) {
        Optional<Customer> findOne = customerRepository.findByEmail(email);

        if (findOne.isPresent()) {
            throw new RuntimeException(OCCUPIED_EMAIL.getMessage());
        }

        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerRepository.insert(customer);


        return entityToDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto update(Customer customer) {
        Customer updateCustomer = customerRepository.update(customer);
        return entityToDto(updateCustomer);
    }

    @Override
    public CustomerDto findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_FOUND.getMessage()));

        List<Voucher> wallet = walletRepository.findVouchersByCustomerId(customerId);

        customer.makeWallet(wallet);

        return entityToDto(customer);
    }

    @Override
    public CustomerDto findByName(String name) {
        Customer customer = customerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_FOUND.getMessage()));

        return entityToDto(customer);
    }

    @Override
    public CustomerDto findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_FOUND.getMessage()));

        return entityToDto(customer);
    }

    @Override
    public CustomerDto findCustomerByVoucherId(UUID voucherId) {
        Customer customer = walletRepository.findCustomerByVoucherId(voucherId)
                .orElseThrow(() -> new RuntimeException(NOT_FOUND_ERROR.getMessage()));

        return entityToDto(customer);
    }

    @Override
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(customer -> entityToDto(customer))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteCustomer(customerId);
    }

}
