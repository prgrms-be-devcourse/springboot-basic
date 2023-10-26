package org.programmers.springorder.repository.customer;

import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_CUSTOMER = "INSERT INTO customers(customer_id, customer_name, customer_type) VALUES(:customerId, :customerName, :customerType)";
    private static final String SELECT_ALL_CUSTOMER = "SELECT * FROM customers";
    private static final String SELECT_BLACKLIST_CUSTOMER = "SELECT * FROM customers where customer_type = 'BLACK'";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers where customer_id = :customer_id";


    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        String customerName = resultSet.getString("customer_name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        return Customer.toCustomer(customerId, customerName, customerType);
    };

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update(INSERT_CUSTOMER, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("고객 저장에 실패했습니다.");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER, customerRowMapper);
    }

    @Override
    public List<Customer> findAllBlackList() {
        return jdbcTemplate.query(SELECT_BLACKLIST_CUSTOMER, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Map<String, Object> param = Map.of("customerId", customerId);
            Customer customer = jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID, param, customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerName", customer.getName());
            put("customerType", customer.getCustomerType().name());
        }};
    }
}