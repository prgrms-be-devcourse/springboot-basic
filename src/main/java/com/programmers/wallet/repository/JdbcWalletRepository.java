package com.programmers.wallet.repository;

import com.programmers.customer.domain.Customer;
import com.programmers.exception.NotFoundException;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void updateVoucherCustomerId(UUID customerId, UUID voucherId) {
        String sql = "update vouchers set customer_id = ? WHERE id = ?";

        jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        String sql = "select * from vouchers where customer_id = ?";

        return jdbcTemplate.query(sql, voucherRowMapper(), customerId.toString());
    }

    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        String sql = "select * from customers where id = (select customer_id from vouchers where id = ?)";

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), voucherId.toString());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteVoucherByVoucherIdAndCustomerId(UUID voucherId, UUID customerId) {
        String sql = "delete from vouchers where id = ? and customer_id = ?";

        jdbcTemplate.update(sql, voucherId.toString(), customerId.toString());
    }

    public void deleteAllVouchersByCustomerId(UUID customerId) {
        String sql = "delete from vouchers where customer_id = ?";

        jdbcTemplate.update(sql, customerId.toString());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            long value = rs.getLong("value");
            String type = rs.getString("type");
            String customerId = rs.getString("customer_id");

            if (customerId == null) {
                return VoucherType.createVoucher(type, id, name, value, Optional.empty());
            } else {
                UUID customerUUID = UUID.fromString(customerId);
                return VoucherType.createVoucher(type, id, name, value, Optional.of(customerUUID));
            }
        };
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) ->
                new Customer(UUID.fromString(rs.getString("id")),
                        rs.getString("name"));
    }
}
