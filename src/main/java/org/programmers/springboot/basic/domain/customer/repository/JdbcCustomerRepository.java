package org.programmers.springboot.basic.domain.customer.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.CustomerType;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public JdbcCustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
        System.out.println("datasource: " + this.dataSource.getClass().getName());
    }

    private static RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUIDConverter.toUUID(rs.getBytes("customer_id"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            CustomerType customerType = CustomerType.valueOf(rs.getInt("isBlack"));
            return new Customer(customerId, name, email, customerType);
        };
    }

    @Override
    public void add(final Customer customer) {
        String sql = "INSERT INTO customers (customer_id, name, email, isBlack) VALUES(UUID_TO_BIN(?), ?, ?, ?)";
        this.jdbcTemplate.update(sql,
                customer.getCustomerId().toString().getBytes(),
                customer.getName(), customer.getEmail(),
                customer.getCustomerTypeValue());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, customerRowMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No customer found for email: {}", email);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, customerRowMapper(), customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No customer found for customerId: {}", customerId);
        } catch (DataAccessException e) {
            log.error("Data access Exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        return this.jdbcTemplate.query(sql, customerRowMapper());
    }

    @Override
    public List<Customer> getBlack() {
        String sql = "SELECT * FROM customers WHERE isBlack = TRUE";
        return this.jdbcTemplate.query(sql, customerRowMapper());
    }

    @Override
    public void update(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, isBlack = ? WHERE customer_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getCustomerTypeValue(), customer.getCustomerId().toString().getBytes());
    }

    @Override
    public void delete(Customer customer) {
        String sql = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, (Object) customer.getCustomerId().toString().getBytes());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM customers";
        this.jdbcTemplate.update(sql);
    }
}
