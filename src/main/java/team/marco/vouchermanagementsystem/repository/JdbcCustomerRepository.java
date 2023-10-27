package team.marco.vouchermanagementsystem.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.Customer;
import team.marco.vouchermanagementsystem.util.UUIDConverter;

@Repository
public class JdbcCustomerRepository {
    private static final RowMapper<Customer> customerRowMapper = JdbcCustomerRepository::mapToCustomer;

    private static Customer mapToCustomer(ResultSet resultSet, int ignored) throws SQLException {
        UUID id = UUIDConverter.convert(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Timestamp lastLoginAt = resultSet.getTimestamp("last_login_at");
        LocalDateTime createAt = resultSet.getTimestamp("create_at").toLocalDateTime();

        if (!Objects.isNull(lastLoginAt)) {
            return new Customer(id, name, email, lastLoginAt.toLocalDateTime(), createAt);
        }

        return new Customer(id, name, email, null, createAt);
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(Customer customer) {
        return jdbcTemplate.update(
                "INSERT INTO customers(id, name, email, created_at)"
                        + " VALUES (UUID_TO_BIN(:id), :name, :email, :cratedAt)",
                customerToMap(customer));
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
    }

    public int update(Customer customer) {
        return jdbcTemplate.update(
                "UPDATE customers SET name = :name, email = :email, last_login_at = :lastLoginAt"
                        + " WHERE id = UUID_TO_BIN(:id)",
                customerToMap(customer));
    }

    public Optional<Customer> findById(String id) {
        Customer customer = jdbcTemplate.queryForObject(
                "SELECT * FROM customer"
                        + " WHERE id = UUID_TO_BIN(:id)",
                Collections.singletonMap("id", id.getBytes()),
                customerRowMapper);

        return Optional.ofNullable(customer);
    }

    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM customer"
                        + " WHERE name LIKE %:name%",
                Collections.singletonMap("name", name),
                customerRowMapper);
    }

    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(
                "SELECT * FROM customer"
                        + " WHERE email LIKE %:email%",
                Collections.singletonMap("email", email),
                customerRowMapper);
    }

    private Map<String, Object> customerToMap(Customer customer) {
        return Map.ofEntries(
                Map.entry("id", customer.getId().toString().getBytes()),
                Map.entry("name", customer.getName()),
                Map.entry("email", customer.getEmail()),
                Map.entry("lastLoginAt", customer.getLastLoginAt()),
                Map.entry("createdAt", customer.getCreatedAt()));
    }
}
