package com.prgrms.voucher_manager.customer.service;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.JdbcCustomer;
import com.prgrms.voucher_manager.customer.repository.BlackCustomerRepository;
import com.prgrms.voucher_manager.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final BlackCustomerRepository blackCustomerRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(BlackCustomerRepository blackCustomerRepository, CustomerRepository customerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
        this.customerRepository = customerRepository;
    }

    public void findAllBlackCustomer(){
        if(blackCustomerRepository.count() == 0) logger.info("등록된 고객이 없습니다.");
        else blackCustomerRepository.findAll();
    }

    public List<Customer> findAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        if(customerRepository.count() == 0) logger.info("등록된 고객이 없습니다.");
        else {
            customers = customerRepository.findAll();
            AtomicInteger i = new AtomicInteger();
            customers.forEach((e) -> {
                System.out.println(i.getAndIncrement() + " : " + e.toString());
            });
        }
        return customers;
    }

    public void createCustomer(String name, String email) {
        Customer newCustomer = new JdbcCustomer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerRepository.insert(newCustomer);
    }

    public void findCustomerByWallet(List<UUID> ids) {
        List<Optional<Customer>> customers = new ArrayList<>();
        ids.forEach(id->{
            Optional<Customer> customer = customerRepository.findById(id);
            customers.add(customer);
        });
        customers.forEach(c -> {
            System.out.println(c.toString());
        });
    }

    public void updateLastLoginDate(Customer customer) {
        customer.loginInNow();
        customerRepository.update(customer);
    }

}
