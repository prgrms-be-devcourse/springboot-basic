package org.prgrms.kdt.customer;


import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.wallet.Wallet;
import org.prgrms.kdt.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;

    public CustomerService(CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getBlackList() {
        return customerRepository.findBlackList();
    }

    public List<Wallet> getHaveVouchers(UUID customerId) {
        return walletRepository.findByCustomerId(customerId.toString());
    }
}
