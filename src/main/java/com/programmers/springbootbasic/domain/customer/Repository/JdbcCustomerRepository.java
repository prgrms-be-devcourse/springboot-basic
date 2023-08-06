package com.programmers.springbootbasic.domain.customer.Repository;

import com.programmers.springbootbasic.domain.customer.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String FAIL_TO_CREATE = "유저 생성에 실패했습니다. 입력값을 확인해주세요";
    private static final String FAIL_TO_UPDATE = "유저 업데이트에 실패했습니다. 입력값을 확인해주세요";
    private static final String DATABASE_CUSTOMER_ID = "customer_id";
    private static final String CUSTOMER_ID = "customerId";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> rowMapper = (rs, rowNum) -> {
        UUID customerId = UUID.fromString(rs.getString(DATABASE_CUSTOMER_ID));
        String email = rs.getString(EMAIL);
        String name = rs.getString(NAME);
        return new Customer(customerId, email, name);
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(Customer customer) {
        int affectedRow = jdbcTemplate.update(
                "INSERT INTO customer(customer_id, name, email) VALUES(:customerId, :name, :email)",
                toParamMap(customer));
        if (affectedRow != 1) {
            throw new IllegalArgumentException(FAIL_TO_CREATE);
        }
        return customer.getCustomerId();
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                CUSTOMER_ID, customer.getCustomerId().toString(),
                NAME, customer.getName(),
                EMAIL, customer.getEmail()
        );
    }

    @Override
    public void update(Customer customer) {
        int affectedRow = jdbcTemplate.update(
                "UPDATE customer SET name = :name, email = :email WHERE customer_id = :customerId",
                toParamMap(customer)
        );
        if (affectedRow != 1) {
            throw new IllegalArgumentException(FAIL_TO_UPDATE);
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            Customer customer = jdbcTemplate.queryForObject(
                    "SELECT * FROM customer WHERE email = :email",
                    Collections.singletonMap(EMAIL, email),
                    rowMapper
            );
            return Optional.ofNullable(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
