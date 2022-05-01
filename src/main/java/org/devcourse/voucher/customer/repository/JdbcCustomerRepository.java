package org.devcourse.voucher.customer.repository;

import org.devcourse.voucher.customer.model.Customer;
import org.devcourse.voucher.customer.model.Email;
import org.devcourse.voucher.utils.JdbcUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Profile("prod")
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BlacklistRepository blacklistRepository;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate, BlacklistRepository blacklistRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.blacklistRepository = blacklistRepository;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = JdbcUtils.toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        Email email = resultSet.getObject("email", Email.class);
        return new Customer(customerId, name, email);
    };

    private static final Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail().getAddress());
        return paramMap;
    }

    @Override
    public Customer insert(Customer customer) {
        int inserted = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email) VALUES(UUID_TO_BIN(:customerId), :name, :email)",
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
