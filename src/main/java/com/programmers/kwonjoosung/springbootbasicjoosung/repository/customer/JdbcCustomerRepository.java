package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataAlreadyExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataNotExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerTable.CUSTOMER_ID;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerTable.NAME;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public static final String CUSTOMER = "customer";
    private static final int FAIL = 0;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(
                    UUID.fromString(rs.getString(CUSTOMER_ID.getColumnName())),
                    rs.getString(NAME.getColumnName())
    );

    @Override
    public Customer insert(Customer customer) {
        final String sql = "INSERT INTO customers (customer_id, name) VALUES (:customer_id,:name)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customer.getCustomerId().toString())
                .addValue(NAME.getColumnName(), customer.getName());
        try {
            jdbcTemplate.update(sql, parameters); // FAIL이 나올 수 있나?
            return customer;
        } catch (DuplicateKeyException e) {
            throw new DataAlreadyExistException(customer.getCustomerId().toString(), CUSTOMER);
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        final String sql = "SELECT * FROM customers WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customerId.toString());
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        final String sql = "SELECT * FROM customers";
        try {
            return jdbcTemplate.query(sql, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Customer update(Customer customer) {
        final String sql = "UPDATE customers SET name = :name WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customer.getCustomerId().toString())
                .addValue(NAME.getColumnName(), customer.getName());
        if(jdbcTemplate.update(sql, parameters) == FAIL) {
            throw new DataNotExistException(customer.getCustomerId().toString(), CUSTOMER);
        }
        return customer;

    }

    @Override
    public void delete(UUID customerId) {
        final String sql = "DELETE FROM customers WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customerId.toString());
        if(jdbcTemplate.update(sql, parameters) == FAIL) {
            throw new DataNotExistException(customerId.toString(), CUSTOMER);
        }
    }

}