package prgms.spring_week1.domain.customer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;
import prgms.spring_week1.domain.customer.repository.impl.sql.CustomerManageSql;
import prgms.spring_week1.domain.voucher.repository.impl.JdbcVoucherRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcCustomerRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultset, i) -> {
        var name = resultset.getString("name");
        var email = resultset.getString("Email");
        return new Customer(name, new Email(email));
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("email", customer.getEmail());
            put("name", customer.getName());
        }};
    }

    @Override
    public void insert(Customer customer) {
        var update = jdbcTemplate.update(CustomerManageSql.insertNewCustomerSQL,
                toParamMap(customer));

        if (update != 1) {
            throw new RuntimeException("추가된 바우처가 없습니다.");
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return jdbcTemplate.query(CustomerManageSql.findAllCustomerSQL, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("조회된 바우처 리스트가 없습니다.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(CustomerManageSql.findByEmailSQL,
                    Collections.singletonMap("email", email),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("조회된 회원 리스트가 없습니다.", e);
            return Optional.empty();
        }
    }

}
