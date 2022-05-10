package com.prgrms.voucher_manager.customer.service;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.SimpleCustomer;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
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
import java.util.stream.Collectors;

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

    public List<CustomerDto> findAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        AtomicInteger i = new AtomicInteger();
        customers.forEach((e) -> {
            logger.info(i.getAndIncrement() + " : " + e.toString());
        });
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> CustomerDto.of(customer))
                .collect(Collectors.toList());
        return customerDtos;
    }

    public CustomerDto createCustomer(String name, String email) {
        Customer newCustomer = new SimpleCustomer(UUID.randomUUID(), name, email, LocalDateTime.now());
        customerRepository.insert(newCustomer);
        return CustomerDto.of(newCustomer);
    }

    public CustomerDto findCustomerById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(RuntimeException::new);
        return CustomerDto.of(customer);
    }

    public List<CustomerDto> findCustomerByWallet(List<UUID> ids) {
        List<CustomerDto> customers = new ArrayList<>();
        ids.forEach(id->{
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(RuntimeException::new);
            customers.add(CustomerDto.of(customer));
        });
        customers.forEach(c -> {
            logger.info(c.toString());
        });
        return customers;
    }

    public CustomerDto updateCustomer(UUID customerId, String name, String email) {

        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(RuntimeException::new);
        customer.changeName(name);
        customer.loginInNow();
        customerRepository.update(customer);
        return CustomerDto.of(customer);
    }

    public void deleteCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(RuntimeException::new);
        customerRepository.delete(customer);
    }


}
