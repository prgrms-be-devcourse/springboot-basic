package com.example.voucher.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import com.example.voucher.domain.Voucher;

@Component
public class JdbcVoucherRepository implements VoucherRepository {

    private final String SAVE_VOUCHER = "INSERT INTO VOUCHER VALUES (:voucherId, :discountValue, :voucherType)";
    private final String FIND_ALL_VOUCHERS = "SELECT * FROM VOUCHER";
    private final String DELETE_ALL_VOUCHERS = "DELETE FROM VOUCHER";
    private final String SELECT_VOUCHER_BY_ID = "SELECT * FROM VOUHER WHERE VOUCHER_ID = :voucherId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucher.getVoucherId().toString())
            .addValue("discountValue", voucher.getDiscountValue())
            .addValue("voucherType", voucher.getType().toString());

        jdbcTemplate.update(SAVE_VOUCHER, parameterSource);

        return voucher;
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
            Voucher.Type voucherType = Voucher.Type.valueOf(rs.getString("voucher_type"));
            Long discountValue = rs.getLong("discount_value");

            return new Voucher(voucherID, voucherType, discountValue);
        };
    }

}
