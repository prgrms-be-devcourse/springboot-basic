package com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.WrongFindDataException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("release")
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String NOT_FOUND_ID_MESSAGE = "해당 ID는 존재하지 않는 ID입니다.";

    private static final String SAME_ID_MESSAGE = "이미 존재하는 ID입니다.";
    private static final String TABLE_FIELD_CUSTOMER_ID = "customer_id";
    private static final String TABLE_FIELD_NAME = "name";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> customerRowMapper =
            (rs, rowNum) -> new Customer(
                    UUID.fromString(rs.getString(TABLE_FIELD_CUSTOMER_ID)),
                    rs.getString(TABLE_FIELD_NAME)
            );

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Customer customer) {
        final String sql = "INSERT INTO customers (customer_id, name) VALUES (:customer_id,:name)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customer.getCustomerId())
                .addValue(TABLE_FIELD_NAME, customer.getName());
        try {

            return jdbcTemplate.update(sql, parameters) == 1;
        }catch (DuplicateKeyException e) {
            throw new WrongFindDataException(SAME_ID_MESSAGE);
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        final String sql = "SELECT * FROM customers WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customerId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameters, customerRowMapper));
        } catch (DataAccessException e) {
            throw new WrongFindDataException(NOT_FOUND_ID_MESSAGE);
        }
    }

    @Override
    public List<Customer> findAll() {
        final String sql = "SELECT * FROM customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public boolean update(Customer customer) {
        final String sql = "UPDATE customers SET name = :name WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customer.getCustomerId())
                .addValue(TABLE_FIELD_NAME, customer.getName());
        return jdbcTemplate.update(sql, parameters) == 1;
//        throw new WrongFindDataException(NOT_FOUND_ID_MESSAGE);
    }

    @Override
    public boolean delete(UUID customerId) {
        final String sql = "DELETE FROM customers WHERE customer_id = :customer_id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TABLE_FIELD_CUSTOMER_ID, customerId);
        return jdbcTemplate.update(sql, parameters) == 1;
    }

}