package com.programmers.springmission.voucher.repository;

import com.programmers.springmission.voucher.domain.Voucher;
import com.programmers.springmission.voucher.domain.VoucherPolicy;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        VoucherPolicy voucherPolicy = VoucherType.mapperVoucherPolicy(VoucherType.valueOf(resultSet.getString("policy")));
        long voucherAmount = resultSet.getLong("amount");

        Voucher voucher = new Voucher(voucherId, voucherPolicy, voucherAmount);
        if (resultSet.getString("customer_id") != null) {
            voucher.assignVoucherToCustomer(UUID.fromString(resultSet.getString("customer_id")));
        }

        return voucher;
    };

    @Override
    public void save(Voucher voucher) {
        jdbcTemplate.update(
                "INSERT INTO vouchers (voucher_id, policy, amount) VALUES (?, ?, ?)",
                voucher.getVoucherId().toString(),
                voucher.getVoucherPolicy().getVoucherType().toString(),
                voucher.getVoucherAmount());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = ?",
                    voucherRowMapper,
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers",
                voucherRowMapper);
    }

    @Override
    public void update(Voucher voucher) {
        jdbcTemplate.update(
                "UPDATE vouchers SET amount = ? WHERE voucher_id = ?",
                voucher.getVoucherAmount(),
                voucher.getVoucherId().toString());
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = ?",
                voucherId.toString());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM vouchers");
    }
}
