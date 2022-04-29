package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.utils.JdbcUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = JdbcUtils.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        return new Customer(customerId, name);
    };

    private static final Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId());
        paramMap.put("name", customer.getName());
        return paramMap;
    }
    @Override
    public Customer insert(Customer customer) {
        int inserted = jdbcTemplate.update("INSERT INTO customers VALUES(UUID_TO_BIN(:customerId), :name)",
                toParamMap(customer));
        if (inserted != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }
}
