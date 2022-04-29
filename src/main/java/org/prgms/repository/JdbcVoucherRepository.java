package org.prgms.repository;

import org.prgms.domain.FixedAmountVoucher;
import org.prgms.domain.PercentDiscountVoucher;
import org.prgms.domain.Voucher;
import org.prgms.domain.VoucherType;
import org.prgms.utils.UuidUtils;
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

        VoucherType voucherType = getVoucherType(voucher);

        jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount, voucher_kind, created_at) values(?, ?, ?, ?)",
                UuidUtils.uuidToBytes(voucher.getVoucherId()),
                voucher.getDiscountAmount(),
                voucherType.name(),
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

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = ?", UuidUtils.uuidToBytes(voucherId));
    }

    private Voucher mapToVoucher(ResultSet rs, int rowNum) throws SQLException {

        UUID voucherId = UuidUtils.bytesToUUID(rs.getBytes("voucher_id"));
        int amount = rs.getInt("amount");
        String voucherKind = rs.getString("voucher_kind");

        return decideVoucherType(voucherKind, amount, voucherId);
    }

    private Voucher decideVoucherType(String voucherKind, long amount, UUID voucherId) {
        if (voucherKind.equals(VoucherType.FIXED.name()))
            return new FixedAmountVoucher(voucherId, amount);

        else if (voucherKind.equals(VoucherType.PERCENT.name()))
            return new PercentDiscountVoucher(voucherId, amount);

        else
            throw new IllegalArgumentException(MessageFormat.format("voucher Kind의 값이 잘못되었습니다. : {0}", voucherKind));
    }

    private VoucherType getVoucherType(Voucher voucher) {
        if (voucher.getClass().getSimpleName().equals(FixedAmountVoucher.class.getSimpleName())) {
            return VoucherType.FIXED;
        } else if (voucher.getClass().getSimpleName().equals(PercentDiscountVoucher.class.getSimpleName())) {
            return VoucherType.PERCENT;
        } else {
            throw new IllegalArgumentException(MessageFormat.format(
                    "해당하는 Voucher 종류의 Enum 값이 존재하지 않습니다. : {0}",
                    voucher.getClass().getSimpleName()));
        }

    }
}
