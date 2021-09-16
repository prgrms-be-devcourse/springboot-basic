package prgms.springbasic.NomalCustomer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM customers";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM customers WHERE customer_id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM customers WHERE name = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM customers WHERE email = ?";
    private static final String DELETE_QUERY = "DELETE FROM customers";
    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_QUERY,
                customer.getCustomerId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(UPDATE_QUERY,
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null,
                customer.getCustomerId().toString().getBytes()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper());
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        List<Customer> result = jdbcTemplate.query(FIND_BY_ID_QUERY, rowMapper(), customerId);
        return result.stream().findAny();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> result = jdbcTemplate.query(FIND_BY_NAME_QUERY, rowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        List<Customer> result = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, rowMapper(), email);
        return result.stream().findAny();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_QUERY);
    }

    private RowMapper<Customer> rowMapper() {
        return (resultSet, rowNum) -> {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            UUID customerId = UUID.fromString("customer_id");
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

            return new Customer(customerId, name, email, lastLoginAt, createdAt);
        };
    }
}
