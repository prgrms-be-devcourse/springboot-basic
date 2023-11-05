package org.programmers.springorder.repository.customer;

import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.model.customer.Customer;
import org.programmers.springorder.model.customer.CustomerType;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static org.programmers.springorder.global.utils.UUIDUtil.toUUID;
import static org.programmers.springorder.global.utils.UUIDUtil.uuidToBytes;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_CUSTOMER = "INSERT INTO customers(customer_id, customer_name, customer_type) VALUES (UUID_TO_BIN(:customerId), :customerName, :customerType)";
    private static final String SELECT_ALL_CUSTOMER = "SELECT * FROM customers";
    private static final String SELECT_BLACKLIST_CUSTOMER = "SELECT * FROM customers where customer_type = 'BLACK'";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_ALL_CUSTOMER = "DELETE FROM customers";
    private static final String DELETE_CUSTOMER_BY_ID = "DELETE FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";


    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("customer_name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        return Customer.toCustomer(customerId, customerName, customerType);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", uuidToBytes(customer.getCustomerIdToString()));
        paramMap.put("customerName", customer.getName());
        paramMap.put("customerType", customer.getCustomerTypeName());
        return paramMap;
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_CUSTOMER, toParamMap(customer));
        if (update != 1) {
            throw new DataAccessException(ErrorMessage.ERROR_IN_SAVE_CUSTOMER) {
            };
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
            Map<String, Object> param = Map.of("customerId", customerId.toString());
            Customer customer = jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID, param, customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_CUSTOMER, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID customerId) {
        Map<String, Object> param = Map.of("customerId", customerId.toString());
        jdbcTemplate.update(DELETE_CUSTOMER_BY_ID, param);
    }

}