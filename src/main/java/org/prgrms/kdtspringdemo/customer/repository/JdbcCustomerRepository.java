package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Profile("dev")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private final String DELETE_SQL = "DELETE FROM customers";
    private final String VERSION_8_0_UUID = "UUID_TO_BIN(:customerId)";
    private final String VERSION_5_7_UUID = "UNHEX(REPLACE(:customerId, '-', ''))";
    private final String CURRENT_UUID = VERSION_5_7_UUID;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var type = resultSet.getString("type");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, type, createdAt);
    };

    private String getVersion57UUID(String value) {
        return "UNHEX(REPLACE(:" + value + ", '-', ''))";
    }

    private Map<String, Object> toparamMap(Customer customer) {
        return new HashMap<String, Object>() {{
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("type", customer.getType());
            put("customerId", customer.getId().toString().getBytes());
            put("createAt", Timestamp.valueOf(customer.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)));
            put("lastLoginAt", customer.getLast_loginAt() != null ? Timestamp.valueOf(customer.getCreatedAt().truncatedTo(ChronoUnit.MILLIS)) : null);
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update("INSERT INTO customers(customer_id, name, email, type, created_at) VALUES (" + CURRENT_UUID + ", :name, :email, :type, :createAt)",
                toparamMap(customer));
        System.out.println(jdbcTemplate.toString());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update("UPDATE customers SET name = :name, email = :email, type = :type, created_at = :createAt WHERE customer_id = " + CURRENT_UUID + ";",
                toparamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM customers", Collections.emptyMap(), Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper);
    }

    @Override
    public List<Customer> findBlacklist() {
        return jdbcTemplate.query("SELECT * FROM customers WHERE type = 'Black'", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id = " + CURRENT_UUID,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE name = :name",
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByType(String type) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE type = :type",
                    Collections.singletonMap("type", type),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(String voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers LEFT JOIN wallets ON wallets.voucher_id = " + getVersion57UUID("voucherId") + " WHERE wallets.customer_id = customers.customer_id",
                    Collections.singletonMap("voucherId", voucherId.getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Get empty result(findByVoucherId)", e);
            return Optional.empty();
        }
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allCustomers.add(new Customer(customerId, customerName, email, "Normal", lastLoginAt, createdAt));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());
    }
}
