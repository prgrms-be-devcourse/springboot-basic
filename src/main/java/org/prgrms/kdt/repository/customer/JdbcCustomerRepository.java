package org.prgrms.kdt.repository.customer;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerType;
import org.prgrms.kdt.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
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
        INSERT INTO customers(customer_id, name, email, created_at, customer_type)
        VALUES(UUID_TO_BIN(:customerId), :name, :email, :createdAt, :customerType)""";
    private String UPDATE_TYPE = """
        UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt 
        WHERE customer_id = UUID_TO_BIN(:customerId)""";
    private String SELECT_ALL = "SELECT * FROM customer";
    private String SELECT_BY_ID = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)";
    private String SELECT_BY_TYPE = "SELECT * FROM customer WHERE customer_type = :customerType";
    private String DELETE_ALL = "DELETE FROM customer";
    private String SELECT_BY_VOUCHER_ID = """
        SELECT customer.* from customer
        INNER JOIN (SELECT customer_id FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)) AS sub_wallet
        ON customer.customer_id = sub_wallet.customer_id""";


    public JdbcCustomerRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                SELECT_BY_ID,
                Map.of("customerId", customerId.toString().getBytes()),
                customerRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
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
    public Customer updateType(Customer customer) {
        var update = jdbcTemplate.update(
            UPDATE_TYPE,
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
