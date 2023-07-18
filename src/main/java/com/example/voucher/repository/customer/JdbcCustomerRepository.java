package com.example.voucher.repository.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.domain.customer.Customer;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.repository.QueryBuilder;

@Component
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customer.getCustomerId().toString())
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("customerType", customer.getCustomerType().toString())
            .addValue("createdAt", customer.getCreatedAt());

        String sql = new QueryBuilder().insertInto("CUSTOMER")
            .values("customerId", "name", "email", "customerType", "createdAt")
            .build();

        jdbcTemplate.update(sql, parameterSource);

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        RowMapper<Customer> custoemrRowMapper = custoemrRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("CUSTOMER")
            .build();

        return jdbcTemplate.query(sql, custoemrRowMapper);
    }

    @Override
    public void deleteAll() {
        SqlParameterSource parameterSource = new MapSqlParameterSource();

        String sql = new QueryBuilder()
            .delete("CUSTOMER")
            .build();

        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public Customer findById(UUID customerID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("customerId", customerID.toString());

        RowMapper<Customer> custoemrRowMapper = custoemrRowMapper();

        String sql = new QueryBuilder().select("*")
            .from("CUSTOMER")
            .where("CUSTOMER_ID", "=", "customerId")
            .build();

        return jdbcTemplate.queryForObject(sql, parameterSource, custoemrRowMapper);
    }

    private RowMapper<Customer> custoemrRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            String customerName = rs.getString("customer_name");
            String customerEmail = rs.getString("customer_email");
            CustomerType customerType = CustomerType.valueOf(rs.getString("customer_type"));
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return new Customer(customerId, customerName, customerEmail, customerType, createdAt);
        };
    }

}
