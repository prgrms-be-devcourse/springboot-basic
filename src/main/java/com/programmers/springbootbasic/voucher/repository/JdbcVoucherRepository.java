package com.programmers.springbootbasic.voucher.repository;

import com.programmers.springbootbasic.exception.NotFoundException;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import com.programmers.springbootbasic.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"jdbc", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers(id, name, value, type) values (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getVoucherName(),
                voucher.getVoucherValue(),
                voucher.getVoucherType().toString());

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";

        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = "select * from vouchers where id = ?";

        try {
            Voucher voucher = jdbcTemplate.queryForObject(sql, voucherRowMapper(), id.toString());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("[ERROR] 바우처가 존재하지 않습니다.");
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        String sql = "update vouchers set name = ?, value = ? where id = ?";

        jdbcTemplate.update(sql,
                voucher.getVoucherName(),
                voucher.getVoucherValue(),
                voucher.getVoucherId().toString());

        return voucher;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from vouchers where id = ?";

        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";

        jdbcTemplate.update(sql);
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
}
