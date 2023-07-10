package com.dev.bootbasic.voucher.repository;

import com.dev.bootbasic.voucher.domain.FixedAmountVoucher;
import com.dev.bootbasic.voucher.domain.PercentDiscountVoucher;
import com.dev.bootbasic.voucher.domain.Voucher;
import com.dev.bootbasic.voucher.domain.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String INSERT_VOUCHER_COMMAND = "INSERT INTO vouchers (id, voucher_type, discount_amount) VALUES (:id, :voucherType, :discountAmount)";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Voucher> findVoucher(UUID voucherId) {
        return Optional.empty();
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
        return null;
    }
}

