package org.prgrms.kdtspringdemo.domain.customer.service;

import org.prgrms.kdtspringdemo.domain.customer.data.Customer;
import org.prgrms.kdtspringdemo.domain.customer.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomer(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public void findAll() {
        AtomicInteger index = new AtomicInteger(1);
        customerRepository.findAll().stream()
                .forEach((voucher) -> System.out.println((index.getAndIncrement()) + " : " + voucher));
    }

    public int count() {
        int count = customerRepository.count();
        return count;
    }

    public void deleteAll() {
        customerRepository.deleteAll();
        logger.info("Customer data 가 초기화 되었습니다.");
    }

    public Customer findByOrder(int index) {
        List<Customer> allCustomer = customerRepository.findAll();
        return allCustomer.get(index);
    }

    public Optional<Customer> findById(UUID id){
        Optional<Customer> customer = customerRepository.findById(id);
        return customer;
    }

    public void insert(Customer customer){
        customerRepository.insert(customer);
    }
}
