package org.prgrms.kdtspringvoucher.customer.repository;

import org.prgrms.kdtspringvoucher.customer.entity.Customer;
import org.prgrms.kdtspringvoucher.customer.entity.CustomerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerJDBCRepository implements CustomerRepository {

    private static final String SELECT_SQL = "SELECT * FROM customer";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customer WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM customer WHERE name = :name";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM customer WHERE email = :email";
    private static final String INSERT_CUSTOMERS_SQL = "INSERT INTO customer(customer_id, name, email, created_at, customer_type) VALUES (UUID_TO_BIN(:customerId), :name, :email, :createdAt, :customerType)";
    private static final String UPDATE_CUSTOMERS_SQL = "UPDATE customer SET name = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_CUSTOMERS_BY_VOUCHER = "SELECT customer.customer_id AS customer_id, name, email, last_login_at, created_at, customer_type FROM customer JOIN (SELECT * FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)) wallet ON customer.customer_id = wallet.customer_id";
    private static final String SELECT_BLACK_CUSTOMERS = "SELECT * FROM customer WHERE customer_type = :customerType;";

    private static final Logger logger = LoggerFactory.getLogger(CustomerJDBCRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final TransactionTemplate transactionTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var customerTypeNum = resultSet.getInt("customer_type");
        var customerType = CustomerType.WHITE;
        if (customerTypeNum == 2) {
            customerType = CustomerType.BLACK;
        }
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, customerType);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public CustomerJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("customerType", customer.getCustomerType());
        }};
    }

    public void testTransaction(Customer customer) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                jdbcTemplate.update("UPDATE customer SET name = :name WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
                jdbcTemplate.update("UPDATE customer SET email = :email WHERE customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
            }
        });
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_SQL, customerRowMapper);
    }

    @Override
    public List<Customer> findBlackCustomers() {
        return jdbcTemplate.query(SELECT_BLACK_CUSTOMERS,
                Collections.singletonMap("customerType", CustomerType.BLACK.ordinal()),
                customerRowMapper);
    }

    @Override
    public List<Customer> findCustomerByVoucher(UUID voucherId) {
        return jdbcTemplate.query(SELECT_CUSTOMERS_BY_VOUCHER,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
          return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL,
                  Collections.singletonMap("customerId", customerId.toString().getBytes()),
                  customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
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
    public Optional<Customer> findByEmail(String email) {
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
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update(INSERT_CUSTOMERS_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(UPDATE_CUSTOMERS_SQL, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer;
    }
}
