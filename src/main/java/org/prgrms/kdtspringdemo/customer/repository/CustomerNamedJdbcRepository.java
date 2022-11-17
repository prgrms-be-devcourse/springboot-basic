package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.blacklist.model.BlackCustomer;
import org.prgrms.kdtspringdemo.blacklist.repository.BlackListRepository;
import org.prgrms.kdtspringdemo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository, BlackListRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private static final String INSERT_SQL = "INSERT INTO customers_demo(customer_id, name, birth_date, email, created_at,black_list) VALUES(UUID_TO_BIN(:customerId),:name,:birth,:email,:createdAt,:blackList)";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customers_demo WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM customers_demo WHERE email = :email";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM customers_demo WHERE name = :name";
    private static final String FIND_ALL_SQL = "SELECT * FROM customers_demo";
    private static final String UPDATE_SQL = "UPDATE customers_demo SET name = :name,email=:email,last_login_at=:lastLoginAt where customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM customers_demo WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String DELETE_ALL_SQL = "delete FROM customers_demo";
    private static final String FIND_BLACK_LIST_SQL = "SELECT customer_id, email, birth_date FROM customers_id WHERE black_list = true";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM customers_demo";
    private static RowMapper<Customer> customerRawMapper = (resultSet, i) -> {
        var name = resultSet.getString("name");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var email = resultSet.getString("email");
        var birth = resultSet.getDate("birth_date").toLocalDate();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("created_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var blackList = resultSet.getBoolean("black_list");
        return new Customer(customerId, name, birth, email, lastLoginAt, createdAt, blackList);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(customer));
        return customer;
    }

    static UUID toUUID(byte[] bytes) {
        var byterBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byterBuffer.getLong(), byterBuffer.getLong());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, customerRawMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            FIND_BY_ID_SQL,
                            Collections.singletonMap("customerId", customerId.toString().getBytes()),
                            customerRawMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            FIND_BY_EMAIL_SQL,
                            Collections.singletonMap("email", email),
                            customerRawMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            FIND_BY_NAME_SQL, Collections.singletonMap("name", name),
                            customerRawMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int update(Customer customer) {
        var update = jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));
        return update;
    }

    @Override
    public int deleteById(UUID targetId) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, Collections.singletonMap("customerId", targetId.toString().getBytes()));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("birth", customer.getBirth());
            put("email", customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            put("blackList", customer.isBlackCustomer());
        }};
    }

    // BlackListRepository로써의 역할
    @Override
    public List<BlackCustomer> findAllBlackCustomers() {
        return null;
    }
}
