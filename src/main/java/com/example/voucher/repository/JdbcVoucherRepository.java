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
    private final String FIND_BY_ID = "SELECT * FROM VOUCHER WHERE VOUCHER_ID = :voucherId";
    private final String DELETE_BY_ID = "DELETE FROM VOUCHER WHERE VOUCHER_ID = :voucherId";
    private final String FIND_ALL_VOUCHERS = "SELECT * FROM VOUCHER";
    private final String DELETE_ALL_VOUCHERS = "DELETE FROM VOUCHER";
    private final String UPDATE = "UPDATE VOUCHER SET DISCOUNT_VALUE = :discountValue, VOUCHER_TYPE = :voucherType WHERE VOUCHER_ID = :voucherId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId",
                voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getValue())
            .addValue("voucherType", voucher.getVoucherType().toString());

        jdbcTemplate.update(SAVE_VOUCHER, parameterSource);

        UUID voucherID = voucher.getVoucherId();

        return findById(voucherID);
    }

    @Override
    public Voucher findById(UUID voucherID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId", voucherID.toString());

        RowMapper<Voucher> voucherRowMapper = voucherRowMapper();

        return jdbcTemplate.queryForObject(FIND_BY_ID, parameterSource, voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherID) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId", voucherID.toString());

        jdbcTemplate.update(DELETE_BY_ID, parameterSource);
    }

    @Override
    public Voucher update(Voucher voucher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("voucherId",
                voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getValue())
            .addValue("voucherType", voucher.getVoucherType().toString());

        jdbcTemplate.update(UPDATE, parameterSource);

        return findById(voucher.getVoucherId());
    }

    @Override
    public List<Voucher> findAll() {
        RowMapper<Voucher> voucherRowMapper = voucherRowMapper();

        return jdbcTemplate.query(FIND_ALL_VOUCHERS, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        SqlParameterSource parameterSource = new MapSqlParameterSource();
        jdbcTemplate.update(DELETE_ALL_VOUCHERS, parameterSource);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherID = UUID.fromString(rs.getString("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            Long discountValue = rs.getLong("discount_value");

            return VoucherCreator.getVoucher(voucherID, voucherType, discountValue);
        };
    }

}
