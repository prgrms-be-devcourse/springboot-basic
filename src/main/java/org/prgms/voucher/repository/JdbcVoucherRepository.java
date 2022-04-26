package org.prgms.voucher.repository;

import org.prgms.utils.UuidUtils;
import org.prgms.voucher.domain.FixedAmountVoucher;
import org.prgms.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucher.domain.Voucher;
import org.prgms.voucher.domain.VoucherRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
//@Profile("db")
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {

        jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount, voucher_kind, created_at) values(?, ?, ?, ?)",
                UuidUtils.uuidToBytes(voucher.getVoucherId()),
                voucher.getDiscountAmount(),
                voucher.getClass().getSimpleName(),
                LocalDateTime.now());

    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers;", this::mapToVoucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {

            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = ?", this::mapToVoucher, UuidUtils.uuidToBytes(voucherId)));

        } catch (EmptyResultDataAccessException e) {
            // jdbcTemplate.queryForObject 안에서 반환되는 결과가 0일 시 throw 하는 에러

            return Optional.empty();
        }
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers;");
    }


    private Voucher mapToVoucher(ResultSet rs, int rowNum) throws SQLException {

        UUID voucherId = UuidUtils.bytesToUUID(rs.getBytes("voucher_id"));
        int amount = rs.getInt("amount");
        String voucherKind = rs.getString("voucher_kind");

        return decideVoucherType(voucherKind, amount, voucherId);
    }

    private Voucher decideVoucherType(String voucherKind, long amount, UUID voucherId) {
        if (voucherKind.equals(FixedAmountVoucher.class.getSimpleName()))
            return new FixedAmountVoucher(voucherId, amount);

        else if (voucherKind.equals(PercentDiscountVoucher.class.getSimpleName()))
            return new PercentDiscountVoucher(voucherId, amount);

        else
            throw new IllegalArgumentException(MessageFormat.format("voucher Kind의 값이 잘못되었습니다. : {0}", voucherKind));


    }
}
