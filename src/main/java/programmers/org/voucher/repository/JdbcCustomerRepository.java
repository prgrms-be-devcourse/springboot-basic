package programmers.org.voucher.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.Customer;
import programmers.org.voucher.repository.util.InsertBuilder;
import programmers.org.voucher.repository.util.SelectBuilder;
import programmers.org.voucher.repository.util.UpdateBuilder;
import programmers.org.voucher.repository.util.Where;
import programmers.org.voucher.repository.util.statement.*;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

import static programmers.org.voucher.repository.util.constant.Table.CUSTOMERS;

public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        Insert insert = new Insert(CUSTOMERS);

        Values values = new Values.Builder()
                .query("name")
                .query("email")
                .build();

        String sql = new InsertBuilder()
                .insert(insert)
                .values(values)
                .build();

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
        Update update = new Update(CUSTOMERS);

        Set set = new Set.Builder()
                .query("name")
                .build();

        Where where = new Where.Builder()
                .query("customer_id")
                .build();

        String sql = new UpdateBuilder()
                .update(update)
                .set(set)
                .where(where)
                .build();

        jdbcTemplate.update(sql, customer.getName(), customer.getId());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Select select = new Select("*");

        From from = new From(CUSTOMERS);

        Where where = new Where.Builder()
                .query("customer_id")
                .build();

        String sql = new SelectBuilder()
                .select(select)
                .from(from)
                .where(where)
                .build();

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), id);
            return Optional.of(customer);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        Select select = new Select("*");

        From from = new From(CUSTOMERS);

        Where where = new Where.Builder()
                .query("email")
                .build();

        String sql = new SelectBuilder()
                .select(select)
                .from(from)
                .where(where)
                .build();

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
