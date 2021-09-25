package org.prgrms.kdt.customer.repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.model.CustomerType;
import org.prgrms.kdt.utils.UuidUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = UuidUtils.toUUID(resultSet.getBytes("customer_id"));
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
            resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var customerType = CustomerType.valueOf(resultSet.getString("customer_type"));
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, customerType);

    };

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private String INSERT = """
        INSERT INTO customer(customer_id, name, email, created_at, customer_type)
        VALUES(UUID_TO_BIN(:customerId), :name, :email, :createdAt, :customerType)""";
    private String UPDATE = """
        UPDATE customer SET customer_type = :customerType, name = :name
        WHERE customer_id = UUID_TO_BIN(:customerId)""";
    private String SELECT_ALL = "SELECT * FROM customer";
    private String SELECT_BY_ID = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)";
    private String SELECT_BY_TYPE = "SELECT * FROM customer WHERE customer_type = :customerType";
    private String DELETE_ALL = "DELETE FROM customer";
    private String DELETE_BY_ID = "DELETE FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)";
    private String SELECT_BY_VOUCHER_ID = """
        SELECT * FROM customer
        LEFT OUTER JOIN wallet
        ON customer.customer_id = wallet.customer_id
        WHERE wallet.voucher_id = UUID_TO_BIN(:voucherId)""";

    public CustomerJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        var customers = jdbcTemplate.query(
            SELECT_BY_ID,
            Map.of("customerId", customerId.toString().getBytes()),
            customerRowMapper
        );
        return Optional.ofNullable(DataAccessUtils.singleResult(customers));

    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update(
            INSERT,
            toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public List<Customer> findAllCustomer() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }

    @Override
    public List<Customer> findByCustomerType(CustomerType customerType) {
        return jdbcTemplate.query(
            SELECT_BY_TYPE,
            Map.of("customerType", customerType.name()),
            customerRowMapper);
    }

    @Override
    public List<Customer> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(
            SELECT_BY_VOUCHER_ID,
            Map.of("voucherId", voucherId.toString().getBytes()),
            customerRowMapper);
    }


    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(
            UPDATE,
            toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public void deleteAllCustomer() {
        jdbcTemplate.getJdbcTemplate().update(DELETE_ALL);
    }

    @Override
    public void deleteById(UUID customerId) {
        var update = jdbcTemplate.update(
            DELETE_BY_ID,
            Map.of("customerId", customerId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }

    }

    private Map<String, Object> toParamMap(Customer customer) {
        var paramMap = new HashMap<String, Object>();

        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
        paramMap.put(
            "lastLoginAt",
            customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt())
                : null);
        paramMap.put("customerType", customer.getCustomerType().name());

        return paramMap;
    }
}
