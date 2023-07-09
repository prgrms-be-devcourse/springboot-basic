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
            throw new NotFoundException("[ERROR] 회원이 존재하지 않습니다.");
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
        return (rs, rowNum) ->
                VoucherType.createVoucher(rs.getString("type"),
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getLong("value"));
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) ->
                new Customer(UUID.fromString(rs.getString("id")),
                        rs.getString("name"));
    }
}
