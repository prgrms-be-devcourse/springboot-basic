package com.programmers.customer.service;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.CustomerRepository;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Customer join(String name, String email) {
        Optional<Customer> findOne = customerRepository.findByEmail(email);

        if (findOne.isPresent()) {
            throw new RuntimeException("이미 사용중인 이메일입니다.");
        }

        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerRepository.insert(customer);

        return customer;
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public Customer findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Voucher> wallet = walletRepository.findVouchersByCustomerId(customerId);

        customer.makeWallet(wallet);

        return customer;
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
