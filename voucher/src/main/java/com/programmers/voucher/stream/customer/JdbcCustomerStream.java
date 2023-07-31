package com.programmers.voucher.stream.customer;

import com.programmers.voucher.domain.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcCustomerStream implements CustomerStream {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerStream(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String save(Customer customer) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(customer);
        jdbcTemplate.update("INSERT INTO customers(customer_id, name, email) VALUES(:customerId, :name, :email)",
                param);
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        validateCustomerId(customerId);
        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = :customerId",
                    Map.of("customerId", customerId),
                    (rs, rowNum) -> customerRowMapper(rs, rowNum)
            );
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalStateException("데이터베이스 내 문제가 있습니다.");
        }
    }


    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = jdbcTemplate.query("SELECT * FROM customers", ((rs, rowNum) -> customerRowMapper(rs, rowNum)));
        return customerList;

    }


    @Override
    public String update(String customerId, String name) {
        validateCustomerId(customerId);
        jdbcTemplate.update("UPDATE customers SET name = :name WHERE customer_id = :customerId",
                Map.of("name", name, "customerId", customerId));
        return customerId;
    }

    @Override
    public void deleteById(String customerId) {
        validateCustomerId(customerId);
        jdbcTemplate.update("DELETE FROM customers WHERE customer_id = :customerId",
                Map.of("customerId", customerId));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers",
                Map.of());
    }

    private static void validateCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty() || customerId.isBlank()) {
            throw new IllegalStateException("customerId를 입력해주세요");
        }
    }

    private Customer customerRowMapper(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(rs.getString("customer_id"), rs.getString("name"), rs.getString("email"));
    }
}
