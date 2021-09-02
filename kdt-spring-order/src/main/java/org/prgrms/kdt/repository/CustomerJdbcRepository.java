package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final String INSERT_SQL = "insert into customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?), ?, ?, ?)";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String SELECT_SQL_BY_ID = "select * from customers where customer_id = UUID_TO_BIN(?)";
    private final String SELECT_SQL_BY_NAME = "select * from customers where name = ?";
    private final String SELECT_SQL_BY_EMAIL = "select * from customers where email = ?";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String COUNT_SQL = "select count(*) from customers";
    private final String UPDATAE_SQL = "update customers set name = ?, email = ?, last_login_at = ? where customer_id = UUID_TO_BIN(?)";

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, false);
    };

    public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        int executeUpdate = jdbcTemplate.update(INSERT_SQL,
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));

        if (executeUpdate != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer) {

        int executeUpdate = jdbcTemplate.update(UPDATAE_SQL,
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt() != null? Timestamp.valueOf(customer.getLastLoginAt()) : null,
                customer.getId().toString().getBytes());

        if (executeUpdate != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_SQL_BY_ID,
                    customerRowMapper,
                    customerId.toString().getBytes()));
        }
        catch (EmptyResultDataAccessException e){
            logger.error("Got error while connecting db", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_SQL_BY_NAME,
                    customerRowMapper,
                    name));
        }
        catch (EmptyResultDataAccessException e){
            logger.error("Got error while connecting db", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_SQL_BY_EMAIL,
                    customerRowMapper,
                    email));
        }
        catch (EmptyResultDataAccessException e){
            logger.error("Got error while connecting db", e);
            return Optional.empty();
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
