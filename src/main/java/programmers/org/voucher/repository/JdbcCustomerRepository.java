package programmers.org.voucher.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import programmers.org.voucher.domain.Customer;
import programmers.org.voucher.repository.util.Insert;
import programmers.org.voucher.repository.util.Select;
import programmers.org.voucher.repository.util.Update;
import programmers.org.voucher.repository.util.Where;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static programmers.org.voucher.repository.util.constant.Operator.EQUALS;
import static programmers.org.voucher.repository.util.constant.Table.CUSTOMERS;

@Component
class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "?");
        map.put("email", "?");

        Insert insert = Insert.builder()
                .insert(CUSTOMERS)
                .values(map)
                .build();

        String sql = insert.getQuery();

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
        Where where = Where.builder("customer_id", EQUALS, "?").build();

        Update update = Update.builder()
                .update(CUSTOMERS)
                .set("name", "?")
                .where(where)
                .build();

        String sql = update.getQuery();

        jdbcTemplate.update(sql, customer.getName(), customer.getId());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Where where = Where.builder("customer_id", EQUALS, "?").build();

        Select select = Select.builder()
                .select("*")
                .from(CUSTOMERS)
                .where(where)
                .build();

        String sql = select.getQuery();

        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper(), id);

        if (customers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(customers.get(0));
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        Where where = Where.builder("email", EQUALS, "?").build();

        Select select = Select.builder()
                .select("*")
                .from(CUSTOMERS)
                .where(where)
                .build();

        String sql = select.getQuery();

        List<Customer> customers = jdbcTemplate.query(sql, customerRowMapper(), email);

        if (customers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(customers.get(0));
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
