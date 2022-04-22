package org.programmers.kdt.weekly.customer.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.programmers.kdt.weekly.customer.model.Customer;
import org.programmers.kdt.weekly.customer.model.CustomerType;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcCustomerRepository implements CustomerRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email,type) VALUES (UUID_TO_BIN(:customerId), :name, :email, :type)";
    private static final String SELECT_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM customers WHERE email = :customerEmail";
    private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM customers WHERE type = :customerType";
    private static final String UPDATE_SQL = "UPDATE customers SET type = :type WHERE customer_id = UUID_TO_BIN(:customerId)";

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        var update = namedParameterJdbcTemplate.update(INSERT_SQL, toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {

        return namedParameterJdbcTemplate.query(SELECT_SQL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findByEmail(String customerEmail) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                Collections.singletonMap("customerEmail", customerEmail), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("customer findByEmail empty Result", e);

            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByType(String customerType) {
        try {
            return namedParameterJdbcTemplate.query(
                SELECT_BY_TYPE_SQL, Collections.singletonMap("customerType", customerType), customerRowMapper);
        } catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }

    @Override
    public Customer update(Customer customer) {
        var update = namedParameterJdbcTemplate.update(UPDATE_SQL, toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }
}
