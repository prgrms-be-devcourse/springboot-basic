package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.repository.CustomerQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final CustomerQuery customerQuery;
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(CustomerQuery customerQuery, JdbcTemplate jdbcTemplate) {
        this.customerQuery = customerQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void loadCustomers() {
        log.debug("Called JdbcCustomerRepository's loadCustomers method.");
    }

    @Override
    public void persistCustomers() {
        log.debug("Called JdbcCustomerRepository's persistCustomers method.");
    }

    @Override
    public Customer save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement statement = c.prepareStatement(customerQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getUsername());
            statement.setString(2, customer.getAlias());
            statement.setBoolean(3, customer.isBlacklisted());
            return statement;
        }, keyHolder);
        customer.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.debug("Saved customer({}) to repository.", customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(long id) {
        log.debug("Find customer by id: {}", id);
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(customerQuery.getSelect().getById(), customerRowMapper, id);
        } catch (EmptyResultDataAccessException ex) {
            customer = null;
        }

        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> findByVoucher(long voucherId) {
        log.debug("Find customer by voucher: {}", voucherId);
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(customerQuery.getSelect().getByVoucher(), customerRowMapper, voucherId);
        } catch (EmptyResultDataAccessException ex) {
            customer = null;
        }

        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> listAll() {
        log.debug("Find all customers");
        return jdbcTemplate.query(customerQuery.getSelect().getAll(), customerRowMapper);
    }

    private static RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(
            rs.getLong("customer_id"),
            rs.getString("username"),
            rs.getString("alias"),
            rs.getBoolean("blacklisted"),
            rs.getTimestamp("created_at").toLocalDateTime().toLocalDate()
    );
}
