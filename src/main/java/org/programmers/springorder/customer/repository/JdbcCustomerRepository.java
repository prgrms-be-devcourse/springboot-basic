package org.programmers.springorder.customer.repository;

import org.programmers.springorder.customer.model.Customer;
import org.programmers.springorder.customer.model.CustomerType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Primary
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_CUSTOMER = "SELECT * FROM customers";
//    private final String INSERT_CUSTOMER = "INSERT INTO customers(customer_id, customer_name, customer_type) VALUES(:customerId, :customerName, :customerType)";

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        String customerName = resultSet.getString("customer_name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        return Customer.toCustomer(customerId, customerName, customerType);
    };

    @Override
    public List<Customer> findAllBlackList() {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER, customerRowMapper);
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerName", customer.getName());
            put("customerType", customer.getCustomerType().name());
        }};
    }
}