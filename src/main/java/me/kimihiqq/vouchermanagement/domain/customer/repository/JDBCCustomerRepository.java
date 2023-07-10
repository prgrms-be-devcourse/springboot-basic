package me.kimihiqq.vouchermanagement.domain.customer.repository;


import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile({"db", "test"})
@Repository
public class JDBCCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        UUID id = UUID.fromString(rs.getString("id"));
        String name = rs.getString("name");
        String email = rs.getString("email");
        CustomerStatus customerStatus = CustomerStatus.valueOf(rs.getString("customerStatus"));

        return new Customer(id, name, email, customerStatus);
    };
    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers(id, name, email, customerStatus) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getId().toString(), customer.getName(), customer.getEmail(), customer.getCustomerStatus().toString());
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper, customerId.toString());
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public void deleteById(UUID customerId) {
        String sql = "DELETE FROM customers WHERE id = ?";
        jdbcTemplate.update(sql, customerId.toString());
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, customerStatus = ? WHERE id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getCustomerStatus().toString(), customer.getId().toString());
    }

}