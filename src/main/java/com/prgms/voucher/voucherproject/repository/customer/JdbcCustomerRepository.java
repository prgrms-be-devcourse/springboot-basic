package com.prgms.voucher.voucherproject.repository.customer;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.exception.NotFoundCustomerException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgms.voucher.voucherproject.util.JdbcUtils.toUUID;

@Component
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String SQL_INSERT = "INSERT INTO customer(customer_id, email, name, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)";
    private static final String SQL_FINDALL = "SELECT * FROM customer";
    private static final String SQL_FINDBYID = "SELECT * FROM customer WHERE email = ?";
    private static final String SQL_DELETEBYID = "DELETE FROM customer WHERE email = ?";
    public static final int SUCCESS_QUERY = 1;

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        int save = jdbcTemplate.update(SQL_INSERT, customer.getCustomerId().toString().getBytes(), customer.getEmail(),
                customer.getName(), customer.getCreatedAt());

        if (save != SUCCESS_QUERY) {
            throw new IllegalArgumentException("고객 저장에 실패하였습니다.");
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = jdbcTemplate.query(SQL_FINDALL, customerRowMapper);
        return customers;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FINDBYID, customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByEmail(String email) {
        int delete = jdbcTemplate.update(SQL_DELETEBYID, email);

        if (delete != SUCCESS_QUERY) {
            throw new NotFoundCustomerException("삭제할 고객이 존재하지 않습니다.");
        }
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, email, name, createdAt);
    };

}
