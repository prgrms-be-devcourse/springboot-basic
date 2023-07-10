package com.example.voucher.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.example.voucher.constant.VoucherType;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.VoucherCreator;

@Component
public class JdbcVoucherRepository implements VoucherRepository {

    private final String SAVE_VOUCHER = "INSERT INTO VOUCHER VALUES (:voucherId, :discountValue, :voucherType)";
    private final String FIND_ALL_VOUCHERS = "SELECT * FROM VOUCHER";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource parameterSource = voucherParameterSource(voucher);
        jdbcTemplate.update(SAVE_VOUCHER, parameterSource);

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        RowMapper<Voucher> voucherRowMapper = voucherRowMapper();

        return jdbcTemplate.query(FIND_ALL_VOUCHERS, voucherRowMapper);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherID = UUID.fromString(rs.getString("voucher_id"));
            Long discountValue = rs.getLong("discount_value");
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));

            return VoucherCreator.createVoucher(voucherID, discountValue, voucherType);
        };
    }

    private SqlParameterSource voucherParameterSource(Voucher voucher) {
        return new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getDiscountValue())
            .addValue("voucherType", voucher.getVoucherType().toString());
    }

}
