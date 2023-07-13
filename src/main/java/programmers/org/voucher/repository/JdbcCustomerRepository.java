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

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        String sql = "insert into customers(name, email) values(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            return statement;
        }, keyHolder);
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customer set name=? where customer_id=?";
        jdbcTemplate.update(sql, customer.getName(), customer.getId());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        String sql = "select * from customers where customer_id = ?";

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), id);
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "select * from customers where email = ?";

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), email);
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
