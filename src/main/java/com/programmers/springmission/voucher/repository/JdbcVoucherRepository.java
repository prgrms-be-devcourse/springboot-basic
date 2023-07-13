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
            voucher.updateCustomer(UUID.fromString(resultSet.getString("customer_id")));
        }

        return voucher;
    };

    @Override
    public void save(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers (voucher_id, policy, amount) VALUES (?, ?, ?)",
                voucher.getVoucherId().toString(),
                voucher.getVoucherPolicy().getVoucherType().toString(),
                voucher.getVoucherAmount());

        if (update != 1) {
            throw new IllegalArgumentException("바우처 생성에 실패하였습니다.");
        }
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
    public void updateAmount(Voucher voucher) {
        int update = jdbcTemplate.update(
                "UPDATE vouchers SET amount = ? WHERE voucher_id = ?",
                voucher.getVoucherAmount(),
                voucher.getVoucherId().toString());

        if (update != 1) {
            throw new IllegalArgumentException("바우처 가격 수정에 실패하였습니다.");
        }
    }

    @Override
    public void updateCustomer(Voucher voucher) {
        int update = jdbcTemplate.update(
                "UPDATE vouchers SET customer_id = ? WHERE voucher_id = ?",
                voucher.getCustomerId().toString(),
                voucher.getVoucherId().toString());

        if (update != 1) {
            throw new IllegalArgumentException("바우처 고객 할당에 실패하였습니다.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        int update = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = ?",
                voucherId.toString());

        if (update != 1) {
            throw new IllegalArgumentException("바우처 삭제를 실패하였습니다.");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM vouchers");
    }
}
