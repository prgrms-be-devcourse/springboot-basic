package com.prgrms.vouchermanagement.core.customer.repository.jdbc;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("prod")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(id, name, email);
    };

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(id, name, email) VALUES (?, ?, ?)",
                customer.getId(),
                customer.getName(),
                customer.getEmail());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where id = ?",
                customerRowMapper,
                id));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public void deleteById(String id) {
        int update = jdbcTemplate.update("DELETE FROM customers WHERE id = ?",
                id);
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers");
    }

}
