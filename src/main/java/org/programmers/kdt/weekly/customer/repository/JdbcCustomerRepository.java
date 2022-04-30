package org.programmers.kdt.weekly.customer.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {
        var customerId = UtilFunction.toUUID(rs.getBytes("customer_id"));
        var customerEmail = rs.getString("email");
        var customerName = rs.getString("name");
        var customerType = CustomerType.valueOf(rs.getString("type"));

        return new Customer(customerId, customerEmail, customerName, customerType);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> customerMap = new HashMap<>();
        var customerData = customer.serializeCustomer().split(",");
        customerMap.put("customerId", customerData[0]);
        customerMap.put("email", customerData[1]);
        customerMap.put("name", customerData[2]);
        customerMap.put("type", customerData[3]);

        return customerMap;
    }

    @Override
    public Customer insert(Customer customer) {
        String insertSql = "INSERT INTO customers(customer_id, name, email,type) " +
            "VALUES (UNHEX(REPLACE(:customerId, '-', '')), :name, :email, :type)";
        var update = jdbcTemplate.update(insertSql, toParamMap(customer));

        if (update == 0) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String selectSql = "SELECT * FROM customers";

        return jdbcTemplate.query(selectSql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findByEmail(String customerEmail) {
        String selectByEmailSql = "SELECT * FROM customers WHERE email = :customerEmail";

        try {
            var customer = jdbcTemplate.queryForObject(selectByEmailSql,
                Collections.singletonMap("customerEmail", customerEmail), customerRowMapper);

            return Optional.of(customer);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("customer findByEmail empty Result", e);

            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByType(String customerType) {
        String selectByTypeSql = "SELECT * FROM customers WHERE type = :customerType";

        try {
            return jdbcTemplate.query(selectByTypeSql,
                Collections.singletonMap("customerType", customerType), customerRowMapper);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String selectByIdSql = "SELECT * FROM customers WHERE customer_id = :customerId";

        try {
            var customer = jdbcTemplate.queryForObject(selectByIdSql,
                Collections.singletonMap("customerId", customerId), customerRowMapper);

            return Optional.ofNullable(customer);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("customer findById empty Result", e);

            return Optional.empty();
        }
    }

    @Override
    public Customer update(Customer customer) {
        String updateSql = "UPDATE customers SET type = :type WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        var update = jdbcTemplate.update(updateSql, toParamMap(customer));

        if (update == 0) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM customers";

        try {
            jdbcTemplate.update(deleteSql, Collections.emptyMap());
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("customer repository deleteAll error -> {}", e);
        }
    }
}