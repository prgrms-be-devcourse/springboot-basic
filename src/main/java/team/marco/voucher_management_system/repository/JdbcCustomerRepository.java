package team.marco.voucher_management_system.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.util.UUIDConverter;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final RowMapper<Customer> customerRowMapper = JdbcCustomerRepository::mapToCustomer;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Customer mapToCustomer(ResultSet resultSet, int ignored) throws SQLException {
        UUID id = UUIDConverter.convert(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Timestamp lastLoginAt = resultSet.getTimestamp("last_login_at");
        LocalDateTime createAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        LocalDateTime localDateTimeLastLoginAt = null;

        if (!Objects.isNull(lastLoginAt)) {
            localDateTimeLastLoginAt = lastLoginAt.toLocalDateTime();
        }

        return new Customer(id, name, email, localDateTimeLastLoginAt, createAt);
    }

    @Override
    public int create(Customer customer) {
        return jdbcTemplate.update(
                "INSERT INTO customer(id, name, email, last_login_at, created_at)"
                        + " VALUES (UUID_TO_BIN(:id), :name, :email, :lastLoginAt, :createdAt)",
                customerToMap(customer));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
    }

    @Override
    public int update(Customer customer) {
        return jdbcTemplate.update(
                "UPDATE customer SET name = :name, last_login_at = :lastLoginAt"
                        + " WHERE id = UUID_TO_BIN(:id)",
                customerToMap(customer));
    }

    @Override
    public Optional<Customer> findById(String id) {
        try {
            Customer customer = jdbcTemplate.queryForObject(
                    "SELECT * FROM customer"
                            + " WHERE id = UUID_TO_BIN(:id)",
                    Collections.singletonMap("id", id.getBytes()),
                    customerRowMapper);

            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM customer"
                        + " WHERE name LIKE :name",
                Collections.singletonMap("name", withWildCards(name)),
                customerRowMapper);
    }

    @Override
    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(
                "SELECT * FROM customer"
                        + " WHERE email LIKE :email",
                Collections.singletonMap("email", withWildCards(email)),
                customerRowMapper);
    }

    private Map<String, Object> customerToMap(Customer customer) {
        Map<String, Object> customerFields = new HashMap<>() {
            {
                put("id", customer.getId().toString().getBytes());
                put("name", customer.getName());
                put("email", customer.getEmail());
                put("lastLoginAt", null);
                put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            }
        };

        if (!Objects.isNull(customer.getLastLoginAt())) {
            customerFields.put("lastLoginAt", Timestamp.valueOf(customer.getLastLoginAt()));
        }

        return customerFields;
    }

    private String withWildCards(String word) {
        return MessageFormat.format("%{0}%", word);
    }
}
