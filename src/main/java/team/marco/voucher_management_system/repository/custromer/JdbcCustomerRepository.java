package team.marco.voucher_management_system.repository.custromer;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.domain.customer.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static team.marco.voucher_management_system.error.ErrorMessage.CANNOT_CREATE_CUSTOMER;
import static team.marco.voucher_management_system.util.UUIDUtil.bytesToUUID;
import static team.marco.voucher_management_system.util.UUIDUtil.uuidToBytes;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = ?";
    private static final String SELECT_BY_EMAIL_SQL = "SELECT * FROM customers WHERE email = ?";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_SQL,
                uuidToBytes(customer.getId()),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));

        if(update != 1) {
            throw new RuntimeException(CANNOT_CREATE_CUSTOMER);
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, rowNum) -> customers.add(resultSetToCustomer(resultSet)));

        return Collections.unmodifiableList(customers);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                    (resultSet, rowNum) -> resultSetToCustomer(resultSet),
                    uuidToBytes(customerId));

            return Optional.of(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            Customer customer = jdbcTemplate.queryForObject(SELECT_BY_EMAIL_SQL,
                    (resultSet, rowNum) -> resultSetToCustomer(resultSet),
                    email);

            return Optional.of(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Customer resultSetToCustomer(ResultSet resultSet) throws SQLException {
        UUID id = bytesToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer.Builder(name, email)
                .id(id)
                .createdAt(createdAt)
                .build();
    }
}
