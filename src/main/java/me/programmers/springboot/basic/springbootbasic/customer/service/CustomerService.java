package me.programmers.springboot.basic.springbootbasic.customer.service;

import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.model.CustomerInfo;
import me.programmers.springboot.basic.springbootbasic.customer.repository.JdbcTemplateCustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final JdbcTemplateCustomerRepository jdbcTemplateCustomerRepository;

    public CustomerService(JdbcTemplateCustomerRepository jdbcTemplateCustomerRepository) {
        this.jdbcTemplateCustomerRepository = jdbcTemplateCustomerRepository;
    }

    public Customer insert(Customer customer) {
        jdbcTemplateCustomerRepository.insert(customer);
        return customer;
    }

    public Customer findByEmail(String email) {
        return jdbcTemplateCustomerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("email을 찾을 수 없습니다!"));
    }

    public Customer update(UUID uuid, String name, String email) {
        Customer customer = new Customer(uuid, new CustomerInfo(name, email), LocalDateTime.now(), LocalDateTime.now());
        return jdbcTemplateCustomerRepository.update(customer);
    }

    public List<Customer> findAll() {
        return jdbcTemplateCustomerRepository.findAll();
    }

    public void delete() {
        jdbcTemplateCustomerRepository.deleteAll();
    }

}
