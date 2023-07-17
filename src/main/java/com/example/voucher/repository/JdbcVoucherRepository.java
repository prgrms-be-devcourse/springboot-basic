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

@Component
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getValue())
            .addValue("voucherType", voucher.getVoucherType().toString());

        String sql = new QueryBuilder().insertInto("VOUCHER")
            .values("voucherId", "discountValue", "voucherType")
            .build();

        jdbcTemplate.update(sql, parameterSource);
        UUID voucherID = voucher.getVoucherId();

        return findById(voucherID);
    }

    @Override
    public Voucher findById(UUID voucherID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId", voucherID.toString());

        RowMapper<Voucher> voucherRowMapper = voucherRowMapper();

        return jdbcTemplate.queryForObject("SELECT * FROM VOUCHER WHERE VOUCHER_ID = :voucherId", parameterSource,
            voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId", voucherID.toString());

        String sql = new QueryBuilder().delete("VOUCHER")
            .where("VOUCHER_ID", "=", "voucherId")
            .build();

        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public Voucher update(Voucher voucher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getValue())
            .addValue("voucherType", voucher.getVoucherType().toString());

        jdbcTemplate.update(
            "UPDATE VOUCHER SET DISCOUNT_VALUE = :discountValue, VOUCHER_TYPE = :voucherType WHERE VOUCHER_ID = :voucherId",
            parameterSource);

        return findById(voucher.getVoucherId());
    }

    @Override
    public List<Voucher> findAll() {
        RowMapper<Voucher> voucherRowMapper = voucherRowMapper();

        return jdbcTemplate.query("SELECT * FROM VOUCHER", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        SqlParameterSource parameterSource = new MapSqlParameterSource();

        String sql = new QueryBuilder()
            .delete("VOUCHER")
            .build();

        jdbcTemplate.update(sql, parameterSource);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherID = UUID.fromString(rs.getString("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            Long discountValue = rs.getLong("discount_value");

            return voucherType.createVoucher(voucherID, discountValue);
        };
    }

}
