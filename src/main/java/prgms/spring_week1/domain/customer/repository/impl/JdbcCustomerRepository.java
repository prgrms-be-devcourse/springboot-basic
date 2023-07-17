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
import prgms.spring_week1.domain.customer.repository.impl.sql.CustomerManageSql;
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

    private final int VALID_ROW_RESULT = 1;

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
        return new HashMap<>() {{
            put("beforeUpdateEmail", beforeUpdateEmail);
            put("afterUpdateEmail", afterUpdateEmail);
        }};
    }

    @Override
    public void insert(Customer customer) {
        var updatedRowNumber = jdbcTemplate.update(CustomerManageSql.insertNewCustomerSQL,
                toParamMap(customer));

        if (updatedRowNumber != VALID_ROW_RESULT) {
            logger.error("추가된 회원 정보가 없습니다.");
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(CustomerManageSql.findAllCustomerSQL, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("조회된 회원 정보 리스트가 없습니다.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Customer findByEmail(String email) {
        List<Customer> foundCustomer = jdbcTemplate.query(CustomerManageSql.findByEmailSQL, Collections.singletonMap("email", email), customerRowMapper);

        return DataAccessUtils.singleResult(foundCustomer);
    }

    @Override
    public void updateInfo(String beforeUpdateEmail, String afterUpdateEmail) {
        var updatedRowNumber = jdbcTemplate.update(CustomerManageSql.updateCustomerInfoSQL, toEmailParamMap(beforeUpdateEmail, afterUpdateEmail));

        if (updatedRowNumber != VALID_ROW_RESULT) {
            logger.error("회원 정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteByEmail(String email) {
        var updatedRowNumber = jdbcTemplate.update(CustomerManageSql.deleteByEmailSQL, Collections.singletonMap("email", email));

        if (updatedRowNumber != VALID_ROW_RESULT) {
            logger.error("회원 정보를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(CustomerManageSql.deleteAllSQL, new HashMap<>());
    }
}
