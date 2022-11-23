package com.programmers.voucher.repository.customer;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.model.customer.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.programmers.voucher.utils.JdbcParamMapper.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final String findByEmailSql
            = "SELECT * FROM customers WHERE email = :email";
    private static final String findByVoucherSql
            = "SELECT customers.* FROM customers " +
            "LEFT JOIN vouchers ON customers.customer_id = vouchers.customer_id " +
            "WHERE vouchers.voucher_id = UUID_TO_BIN(:voucherId)";

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
    public Customer save(CustomerDto customerDto) {
        Long id = jdbcInsert.executeAndReturnKey(toCustomerMap(customerDto)).longValue();
        return new Customer(id, customerDto.customerName(), customerDto.email());
    }

    @Override
    public Customer findByEmail(String email) {
        return jdbcTemplate.queryForObject(findByEmailSql, toEmailMap(email), rowMapper);
    }

    @Override
    public Customer findByVoucher(UUID voucherId) {
        return jdbcTemplate.queryForObject(findByVoucherSql, toVoucherIdMap(voucherId), rowMapper);
    }
}
