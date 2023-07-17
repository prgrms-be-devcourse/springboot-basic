package com.prgms.voucher.voucherproject.repository.customer;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.exception.DuplicateCustomerException;
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

    public static final int ONLY_ONE_DATA = 1;

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Customer customer) {
        int save = jdbcTemplate.update("INSERT INTO customer(customer_id, email, name, created_at) " +
                        "VALUES (UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(), customer.getEmail(), customer.getName(), customer.getCreatedAt());

        if (save != ONLY_ONE_DATA) {
            throw new DuplicateCustomerException("고객 저장에 실패하였습니다.");
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
        return customers;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM customer WHERE email = ?",
                    customerRowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByEmail(String email) {
        int delete = jdbcTemplate.update("DELETE FROM customer WHERE email = ?", email);

        if (delete != ONLY_ONE_DATA) {
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
