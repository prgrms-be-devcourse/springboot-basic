package prgms.vouchermanagementapp.service;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.repository.JdbcCustomerRepository;

import java.util.Optional;

@Component
public class CustomerService {

    private final JdbcCustomerRepository jdbcCustomerRepository;

    public CustomerService(JdbcCustomerRepository jdbcCustomerRepository) {
        this.jdbcCustomerRepository = jdbcCustomerRepository;
    }

    public Customer save(Customer customer) {
        return jdbcCustomerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByName(String name) {
        return jdbcCustomerRepository.findByName(name);
    }
}
