package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdt.util.UUIDUtils.toUUID;

@Primary
@Qualifier("jdbc")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", customerRowMapper);
    }

    @Override
    public List<Customer> findAllByCustomerType(CustomerType customerType) {
        return jdbcTemplate.query("SELECT * FROM customers WHERE customer_type = :customerType",
            Collections.singletonMap("customerType", customerType.toString()),
            customerRowMapper);
    }

    @Override
    public Customer insert(Customer customer) {
        int insert = jdbcTemplate.update("INSERT INTO customers(customer_id, name, customer_type) VALUES(UUID_TO_BIN(:customerId), :name, :customerType)",
            toParamMap(customer));
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customers");
    }

    private RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String typeName = resultSet.getString("customer_type");
        CustomerType customerType = CustomerType.valueOf(typeName);

        return new Customer(customerId, customerName, customerType);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("customerType", customer.getCustomerType().toString());
        return paramMap;
    }
}
