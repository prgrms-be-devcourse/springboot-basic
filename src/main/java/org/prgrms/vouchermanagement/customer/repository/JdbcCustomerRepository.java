package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private static final String SAVE_SQL = "INSERT INTO customers VALUES (UNHEX(REPLACE(:customerId,'-','')), :name, :email, :createdAt)";
    private static final String UPDATE_SQL = "UPDATE customers SET name = :name, email = :email WHERE customer_id = UNHEX(REPLACE(:customerId,'-',''))";
    private static final String FIND_ALL_SQL = "SELECT * FROM customers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UNHEX(REPLACE(:customerId,'-',''))";
    private static final String FIND_BY_NAME_SQL = "SELECT * FROM customers WHERE name = :name";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM customers WHERE email = :email";
    private static final String COUNT_SAME_EMAIL_SQL = "SELECT COUNT(*) FROM customers WHERE email = :email";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = (resultSet, i) ->
            Customer.createNormalCustomer(
                    toUUID(resultSet.getBytes("customer_id")),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getTimestamp("createdAt").toLocalDateTime()
            );

    private final RowMapper<Integer> countRowMapper = (resultSet, i) -> resultSet.getInt(1);

    @Autowired
    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, customerRowMapper);
    }

    @Override
    public Customer save(Customer customer) {
        try {
            jdbcTemplate.update(SAVE_SQL, toParamMap(customer));
        } catch (DataAccessException e) {
            logger.error("[ERROR] customer save error : {}", e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {

        try {
            jdbcTemplate.update(UPDATE_SQL, toParamMap(customer));
        } catch (DataAccessException e) {
            logger.error("[ERROR] customer update error : {}", e.getMessage());
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {

        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, Map.of("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("[ERROR] data not exist error : {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME_SQL, Map.of("name", name), customerRowMapper);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL, Map.of("email", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("[ERROR] data not exist error : {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean isPresent(String email) {
        return jdbcTemplate.queryForObject(COUNT_SAME_EMAIL_SQL, Map.of("email", email), countRowMapper) > 0;
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of("customerId", customer.getCustomerId().toString().getBytes(),
                "name", customer.getName(),
                "email", customer.getEmail(),
                "createdAt", customer.getCreatedAt());
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
