package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.FixedAmountVoucher;
import com.dev.bootbasic.voucher.domain.PercentDiscountVoucher;
import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String INSERT_VOUCHER_COMMAND = "INSERT INTO vouchers (id, voucher_type, discount_amount) VALUES (:id, :voucherType, :discountAmount)";
    private static final String SELECT_ALL_VOUCHER_QUERY = "SELECT * FROM vouchers";
    private static final String SELECT_ONE_VOUCHER_QUERY = "SELECT * FROM vouchers WHERE id = :voucherId";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Voucher> findVoucher(UUID voucherId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("voucherId", voucherId.toString());

        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(SELECT_ONE_VOUCHER_QUERY, params, getVoucherRowMapper());
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UUID saveVoucher(Voucher voucher) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", voucher.getId().toString());
        params.addValue("voucherType", voucher.getVoucherType().toString());
        params.addValue("discountAmount", voucher.getDiscountAmount());

        namedParameterJdbcTemplate.update(INSERT_VOUCHER_COMMAND, params);
        return voucher.getId();
    }

    @Override
    public Collection<Voucher> getAllVouchers() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_VOUCHER_QUERY, getVoucherRowMapper());
    }

    private RowMapper<Voucher> getVoucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("id"));
            int discountAmount = rs.getInt("discount_amount");
            VoucherType voucherType = VoucherType.from(rs.getString("voucher_type"));
            return switch (voucherType) {
                case FIXED -> FixedAmountVoucher.of(voucherId, discountAmount);
                case PERCENT -> PercentDiscountVoucher.of(voucherId, discountAmount);
            };
        };
    }
}

