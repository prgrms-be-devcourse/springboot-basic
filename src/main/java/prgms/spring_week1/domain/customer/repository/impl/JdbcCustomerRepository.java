package prgms.spring_week1.domain.customer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Dml;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Delete;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Insert;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Select;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Update;
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

    Column column = new Column.ColumnBuilder()
            .columns("customer_id", "email", "name")
            .build();
    Values values = new Values.ValuesBuilder()
            .values("UUID_TO_BIN(:customerId)", ":email", ":name")
            .build();

    @Override
    public void insert(Customer customer) {
        Dml insertSQL = new Insert.InsertBuilder()
                .insert(TableType.customers)
                .columns(column)
                .values(values)
                .build();

        jdbcTemplate.update(insertSQL.toString(), toParamMap(customer));
    }

    @Override
    public List<Customer> findAll() {
        Dml findAllSql = new Select.SelectBuilder()
                .selectAll()
                .from(TableType.customers)
                .build();

        return jdbcTemplate.query(findAllSql.toString(), customerRowMapper);
    }

    Where findWhere = new Where.WhereBuilder()
            .equal("email",":email")
            .build();

    @Override
    public Customer findByEmail(String email) {
        Dml findByEmailSql = new Select.SelectBuilder()
                .selectAll()
                .from(TableType.customers)
                .where(findWhere)
                .build();

        List<Customer> foundCustomer = jdbcTemplate.query(findByEmailSql.toString(), Collections.singletonMap("email", email), customerRowMapper);

        if (foundCustomer.isEmpty()) {
            return null;
        }

        return foundCustomer.get(0);
    }

    Set set = new Set.SetBuilder()
            .set("email",":afterUpdateEmail")
            .build();

    Where updateWhere = new Where.WhereBuilder()
            .equal("email",":beforeUpdateEmail")
            .build();

    @Override
    public void updateInfo(String beforeUpdateEmail, String afterUpdateEmail) {
        Dml updateInfoSql = new Update.UpdateBuilder()
                .update(TableType.customers)
                .set(set)
                .where(updateWhere)
                .build();

        jdbcTemplate.update(updateInfoSql.toString(), toEmailParamMap(beforeUpdateEmail, afterUpdateEmail));
    }

    Where deleteWhere = new Where.WhereBuilder()
            .equal("email",":email")
            .build();

    @Override
    public void deleteByEmail(String email) {
        Dml deleteByEmailSql = new Delete.DeleteBuilder()
                .delete(TableType.customers)
                .where(deleteWhere)
                .build();

        jdbcTemplate.update(deleteByEmailSql.toString(), Collections.singletonMap("email", email));
    }

    @Override
    public void deleteAll() {
        Dml deleteAllSql = new Delete.DeleteBuilder().delete(TableType.customers).build();

        jdbcTemplate.update(deleteAllSql.toString(), new HashMap<>());
    }
}
