package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.customer.dto.CustomerCreateRequest;
import com.programmers.voucher.model.customer.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.utils.JdbcParamMapper.toCustomerMap;
import static com.programmers.voucher.utils.JdbcParamMapper.toEmailMap;
import static com.programmers.voucher.utils.JdbcParamMapper.toVoucherIdMap;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String findByEmailSql
            = "SELECT * FROM customers WHERE email = :email";
    private static final String findByVoucherSql = """
            SELECT customers.* FROM customers 
            LEFT JOIN vouchers ON customers.customer_id = vouchers.customer_id 
            WHERE vouchers.voucher_id = UUID_TO_BIN(:voucherId)""";

    private static final RowMapper<Customer> rowMapper = (resultSet, count) -> {
        int customerId = resultSet.getInt("customer_id");
        String customerName = resultSet.getString("customer_name");
        String email = resultSet.getString("email");
        return new Customer(customerId, customerName, email);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("customers")
                .usingGeneratedKeyColumns("customer_id");
    }

    @Override
    public Customer save(CustomerCreateRequest customerCreateRequest) {
        long id = jdbcInsert.executeAndReturnKey(toCustomerMap(customerCreateRequest)).longValue();
        return new Customer(id, customerCreateRequest.customerName(), customerCreateRequest.email());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(findByEmailSql, toEmailMap(email), rowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> findByVoucher(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(findByVoucherSql, toVoucherIdMap(voucherId), rowMapper));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
