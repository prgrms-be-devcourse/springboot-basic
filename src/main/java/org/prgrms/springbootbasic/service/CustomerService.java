package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.repository.customer.JdbcCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final JdbcCustomerRepository jdbcCustomerRepository;

    public CustomerService(
        JdbcCustomerRepository jdbcCustomerRepository) {
        this.jdbcCustomerRepository = jdbcCustomerRepository;
    }

    public UUID createCustomer(String name, String email) {
        logger.info("createCustomer() called");

        validateDuplicateEmail(email);

        var customerId = jdbcCustomerRepository.save(new Customer(UUID.randomUUID(), name, email));
        return customerId;
    }

    private void validateDuplicateEmail(String email) {
        var customers = jdbcCustomerRepository.findByEmail(email);

        if (customers.isPresent()) {
            throw new IllegalArgumentException("이메일이 중복되었습니다.");
        }
    }

    public List<Customer> findAllCustomers() {
        logger.info("findAllCustomers() called");

        return jdbcCustomerRepository.findAll();
    }
}
