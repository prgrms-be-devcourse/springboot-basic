package com.example.voucher.customer.repository;

import static java.util.Map.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.customer.model.Customer;
import com.example.voucher.query.Delete;
import com.example.voucher.query.Insert;
import com.example.voucher.query.Select;
import com.example.voucher.query.Update;
import com.example.voucher.query.Where;
import com.example.voucher.query.operator.Eq;

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

        Insert insert = Insert.into(Customer.class)
            .values(of(
                "CUSTOMER_ID", ":customerId",
                "CUSTOMER_NAME", ":name",
                "CUSTOMER_EMAIL", ":email",
                "CUSTOMER_TYPE", ":customerType",
                "CREATED_AT", ":createdAt"
            ));

        jdbcTemplate.update(insert.getQuery(), parameterSource);

        return findById(customer.getCustomerId());
    }

    @Override
    public List<Customer> findAll() {
        RowMapper<Customer> custoemrRowMapper = custoemrRowMapper();

        Select select = Select.builder()
            .select("*")
            .from(Customer.class)
            .build();

        return jdbcTemplate.query(select.getQuery(), custoemrRowMapper);
    }

    @Override
    public void deleteAll() {
        SqlParameterSource parameterSource = new MapSqlParameterSource();

        Delete delete = Delete.builder()
            .delete(Customer.class)
            .build();

        jdbcTemplate.update(delete.getQuery(), parameterSource);
    }

    @Override
    public Customer findById(UUID customerID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("customerId", customerID.toString());

        RowMapper<Customer> custoemrRowMapper = custoemrRowMapper();

        Where where = Where.builder()
            .where(new Eq("CUSTOMER_ID", ":customerId"))
            .build();

        Select select = Select.builder()
            .select("*")
            .from(Customer.class)
            .where(where)
            .build();

        return jdbcTemplate.queryForObject(select.getQuery(), parameterSource, custoemrRowMapper);
    }

    @Override
    public void deleteById(UUID customerID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("customerId", customerID.toString());

        Where where = Where.builder()
            .where(new Eq("CUSTOMER_ID", ":customerId"))
            .build();

        Delete delete = Delete.builder()
            .delete(Customer.class)
            .where(where)
            .build();

        jdbcTemplate.update(delete.getQuery(), parameterSource);
    }

    @Override
    public Customer update(Customer customer) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("customerId", customer.getCustomerId().toString())
            .addValue("name", customer.getName())
            .addValue("email", customer.getEmail())
            .addValue("customerType", customer.getCustomerType().toString());

        Where where = Where.builder()
            .where(new Eq("CUSTOMER_ID", ":customerId"))
            .build();

        Update update = Update.builder()
            .updateInto(Customer.class)
            .set(
                of(
                    "CUSTOMER_NAME", ":name",
                    "CUSTOMER_EMAIL", ":email",
                    "CUSTOMER_TYPE", ":customerType"
                )
            )
            .where(where)
            .build();

        jdbcTemplate.update(update.getQuery(), parameterSource);

        return findById(customer.getCustomerId());
    }

    private RowMapper<Customer> custoemrRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            String name = rs.getString("customer_name");
            String email = rs.getString("customer_email");
            CustomerType customerType = CustomerType.valueOf(rs.getString("customer_type"));
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return Customer.builder()
                .customerId(customerId)
                .name(name)
                .email(email)
                .customerType(customerType)
                .createdAt(createdAt)
                .build();

        };
    }

}
