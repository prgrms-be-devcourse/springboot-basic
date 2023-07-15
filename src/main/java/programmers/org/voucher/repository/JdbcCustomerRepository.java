package programmers.org.voucher.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.Customer;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

public class JdbcCustomerRepository implements CustomerRepository {

    private static final String INSERT = "INSERT INTO customers(name, email) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE customer SET name=? WHERE customer_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM customers WHERE customer_id=?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM customers WHERE email=?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT, new String[]{"id"});
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            return statement;
        }, keyHolder);
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update(UPDATE, customer.getName(), customer.getId());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try {
            Customer customer = jdbcTemplate.queryForObject(SELECT_BY_ID, customerRowMapper(), id);
            return Optional.of(customer);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            Customer customer = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, customerRowMapper(), email);
            return Optional.of(customer);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Customer> customerRowMapper() {
        return (resultSet, rowNum) -> {
            Long id = resultSet.getLong("customer_id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            return new Customer(id, name, email);
        };
    }
}
