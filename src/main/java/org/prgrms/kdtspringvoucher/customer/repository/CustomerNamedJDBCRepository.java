package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.JDBCCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerNamedJDBCRepository implements CustomerRepository {

    private static final String SELECT_SQL = "SELECT * FROM customers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM customers WHERE name = :name";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM customers WHERE email = :email";
    private static final String INSERT_CUSTOMERS_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private static final String UPDATE_CUSTOMERS_SQL = "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_CUSTOMERS_SQL = "DELETE FROM customers";

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJDBCRepository.class);


    private final NamedParameterJdbcTemplate jdbcTemplate;

//    private final PlatformTransactionManager platformTransactionManager;

    private final TransactionTemplate transactionTemplate;

    private static final RowMapper<JDBCCustomer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new JDBCCustomer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public CustomerNamedJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    private Map<String, Object> toParamMap(JDBCCustomer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    public void testTransaction(JDBCCustomer customer) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update("UPDATE customers SET name = :name WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
                jdbcTemplate.update("UPDATE customers SET email = :email WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
            }
        });
    }

    @Override
    public List<JDBCCustomer> findAll() {
        return jdbcTemplate.query(SELECT_SQL, customerRowMapper);
    }

    @Override
    public Optional<JDBCCustomer> findById(UUID customerId) {
        try {
          return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                  Collections.singletonMap("customerId", customerId.toString().getBytes()),
                  customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<JDBCCustomer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NAME_SQL,
                    Collections.singletonMap("name", name),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<JDBCCustomer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL,
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public JDBCCustomer insert(JDBCCustomer customer) {
        var update = jdbcTemplate.update(INSERT_CUSTOMERS_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public JDBCCustomer update(JDBCCustomer customer) {
        var update = jdbcTemplate.update(UPDATE_CUSTOMERS_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update(DELETE_CUSTOMERS_SQL);
    }

    @Override
    public int count() {
        return jdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from customers", Integer.class);
    }
}
