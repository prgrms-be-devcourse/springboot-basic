package team.marco.vouchermanagementsystem.repository.custromer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static team.marco.vouchermanagementsystem.util.UUIDUtil.bytesToUUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update(INSERT_SQL,
                customer.getId().toString().getBytes(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreatedAt()));

        if(update != 1) {
            logger.error("사용자를 추가하는 과정에서 오류가 발생했습니다.");
            throw new RuntimeException("사용자를 추가하는 과정에서 오류가 발생했습니다.");
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
                    customerId.toString().getBytes());

            return Optional.of(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Customer resultSetToCustomer(ResultSet resultSet) throws SQLException {
        UUID customerId = bytesToUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, email, createdAt);
    }
}
