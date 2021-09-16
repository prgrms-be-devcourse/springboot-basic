package org.prgrms.kdt.customer;

import org.prgrms.kdt.wallet.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final WalletRepository walletRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }


    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    //customer 생성시, 자동으로 wallet 하나 생성 (기본). => 나중에 지갑을 고객에게 더 추가할 수 있다
    @Override
    public Customer createCustomer(String email, String name) {
        var customerId = UUID.randomUUID();
        var customer = new Customer(customerId, name, email, LocalDateTime.now());
        var returnCustomer =  customerRepository.insert(customer);
        walletRepository.insert(UUID.randomUUID(),customerId);
        return returnCustomer;
    }
}
