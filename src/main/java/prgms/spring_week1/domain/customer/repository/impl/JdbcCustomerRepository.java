package prgms.spring_week1.domain.customer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;
import prgms.spring_week1.domain.util.SqlBuilder;
import prgms.spring_week1.domain.voucher.repository.impl.JdbcVoucherRepository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String insertSQL = new SqlBuilder.InsertBuilder()
                .insert("customers")
                .columns("customer_id","email", "name")
                .values("UUID_TO_BIN(:customerId)",":email",":name")
                .build();

        jdbcTemplate.update(insertSQL, toParamMap(customer));
    }

    @Override
    public List<Customer> findAll() {

        String findAllSql = new SqlBuilder.SelectBuilder()
                    .select("*")
                    .from("customers")
                    .build();

        return jdbcTemplate.query(findAllSql, customerRowMapper);
    }

    @Override
    public Customer findByEmail(String email) {
        String findByEmailSql = new SqlBuilder.SelectBuilder()
                .select("*")
                .from("customers")
                .where("email = :email")
                .build();

        List<Customer> foundCustomer = jdbcTemplate.query(findByEmailSql, Collections.singletonMap("email", email), customerRowMapper);

        if(foundCustomer.size() > 1 || foundCustomer.size() == 0){
            return null;
        }

        return foundCustomer.get(0);
    }

    @Override
    public void updateInfo(String beforeUpdateEmail, String afterUpdateEmail) {
        String updateInfoSql = new SqlBuilder.UpdateBuilder()
                .update("customers")
                .set("email = :afterUpdateEmail")
                .where("email = :beforeUpdateEmail")
                .build();

        jdbcTemplate.update(updateInfoSql, toEmailParamMap(beforeUpdateEmail, afterUpdateEmail));
    }

    @Override
    public void deleteByEmail(String email) {
        String deleteByEmailSql = new SqlBuilder.DeleteBuilder()
                .delete("customers")
                .where("email = :email")
                .build();

        jdbcTemplate.update(deleteByEmailSql, Collections.singletonMap("email", email));
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = new SqlBuilder.DeleteBuilder()
                .delete("customers")
                .build();

        jdbcTemplate.update(deleteAllSql, new HashMap<>());
    }
}
