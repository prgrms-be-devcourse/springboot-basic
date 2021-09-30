package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.exception.CustomerNotInsertedException;
import org.prgrms.kdt.customer.exception.CustomerNotUpdatedException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"dev", "database"})
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(:customerId), :name, :email, :createdAt)";
    private static final String UPDATAE_SQL = "update customers set name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_ALL_SQL = "delete from customers";
    private static final String SELECT_ALL_SQL = "select * from customers";
    private static final String SELECT_BY_ID_SQL = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_BY_NAME_SQL = "select * from customers where name = :name";
    private static final String SELECT_BY_EMAIL_SQL = "select * from customers where email = :email";
    private static final String COUNT_SQL = "select count(*) from customers";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, false);
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer){
        return new HashMap<String, Object>() {
            {
                put("customerId", customer.getId().toString().getBytes());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
                put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            }
        };
    }

    @Override
    public Customer insert(Customer customer) {
        int executeUpdate = jdbcTemplate.update(INSERT_SQL, toParamMap(customer));

        if (executeUpdate != 1) {
            throw new CustomerNotInsertedException(ExceptionMessage.CUSTOMER_NOT_INSERTED.getMessage());
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int executeUpdate = jdbcTemplate.update(UPDATAE_SQL, toParamMap(customer));

        if (executeUpdate != 1) {
            throw new CustomerNotUpdatedException(ExceptionMessage.CUSTOMER_NOT_UPDATED.getMessage());
        }

        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, Collections.emptyMap(), customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                                                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                                                customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL,
                                                                    Collections.singletonMap("name", name),
                                                                    customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                    Collections.singletonMap("email", email),
                    customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int count() {
        try {
            return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
        }
        catch (EmptyResultDataAccessException e){
            return 0;
        }
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
