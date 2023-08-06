package prgms.spring_week1.domain.customer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Delete;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Insert;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Select;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Update;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Column;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Set;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Values;
import prgms.spring_week1.domain.util.type.TableType;
import prgms.spring_week1.domain.voucher.repository.impl.JdbcVoucherRepository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcCustomerRepository implements CustomerRepository {
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final RowMapper<Customer> customerRowMapper = (resultset, i) -> {
        var name = resultset.getString("name");
        var email = resultset.getString("Email");
        return new Customer(name, new Email(email));
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                "customerId", customer.getCustomerId().toString().getBytes(),
                "email", customer.getEmail(),
                "name", customer.getName()
        );
    }

    private Map<String, Object> toEmailParamMap(String beforeUpdateEmail, String afterUpdateEmail) {
        return Map.of(
                "beforeUpdateEmail", beforeUpdateEmail,
                "afterUpdateEmail", afterUpdateEmail
        );
    }

    @Override
    public void insert(Customer customer) {
        Insert insertSQL = Insert.builder()
                .insert(TableType.customers)
                .into(Column
                        .builder()
                        .columns("customer_id", "email", "name")
                        .build())
                .values(Values
                        .builder()
                        .values("UUID_TO_BIN(:customerId)", ":email", ":name")
                        .build())
                .build();

        jdbcTemplate.update(insertSQL.getQuery(), toParamMap(customer));
    }

    @Override
    public List<Customer> findAll() {
        Select findAllSql = Select.builder()
                .selectAll()
                .from(TableType.customers)
                .build();

        return jdbcTemplate.query(findAllSql.getQuery(), customerRowMapper);
    }

    @Override
    public Customer findByEmail(String email) {
        Select findByEmailSql = Select.builder()
                .selectAll()
                .from(TableType.customers)
                .where(Where
                        .builder()
                        .where("email", ":email")
                        .build())
                .build();

        List<Customer> foundCustomer = jdbcTemplate.query(findByEmailSql.getQuery(), Collections.singletonMap("email", email), customerRowMapper);

        if (foundCustomer.isEmpty()) {
            return null;
        }

        return foundCustomer.get(0);
    }

    @Override
    public void updateInfo(String beforeUpdateEmail, String afterUpdateEmail) {
        Update updateInfoSql = Update.builder()
                .update(TableType.customers)
                .set(Set.builder()
                        .set("email", ":afterUpdateEmail")
                        .build())
                .where(Where.builder()
                        .where("email", ":beforeUpdateEmail")
                        .build())
                .build();

        jdbcTemplate.update(updateInfoSql.getQuery(), toEmailParamMap(beforeUpdateEmail, afterUpdateEmail));
    }

    Where deleteWhere = Where.builder()
            .where("email", ":email")
            .build();

    @Override
    public void deleteByEmail(String email) {
        Delete deleteByEmailSql = Delete.builder()
                .deleteFrom(TableType.customers)
                .where(Where.builder()
                        .where("email", ":email")
                        .build())
                .build();

        jdbcTemplate.update(deleteByEmailSql.getQuery(), Collections.singletonMap("email", email));
    }

    @Override
    public void deleteAll() {
        Delete deleteAllSql = Delete.builder()
                .deleteFrom(TableType.customers)
                .build();

        jdbcTemplate.update(deleteAllSql.getQuery(), new HashMap<>());
    }
}
