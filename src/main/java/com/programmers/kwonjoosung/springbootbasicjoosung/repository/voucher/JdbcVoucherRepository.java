package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("release")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String TABLE_FIELD_VOUCHER_ID = "voucher_id";
    private static final String TABLE_FIELD_VOUCHER_TYPE = "voucher_type";
    private static final String TABLE_FIELD_DISCOUNT = "discount";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> VoucherFactory.createVoucher(
            VoucherType.getVoucherType(rs.getString(TABLE_FIELD_VOUCHER_TYPE)),
            UUID.fromString(rs.getString(TABLE_FIELD_VOUCHER_ID)),
            rs.getLong(TABLE_FIELD_DISCOUNT));

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static SqlParameterSource getFullSqlParametersSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue(TABLE_FIELD_VOUCHER_ID, voucher.getVoucherId())
                .addValue(TABLE_FIELD_VOUCHER_TYPE, voucher.getVoucherType().toString())
                .addValue(TABLE_FIELD_DISCOUNT, voucher.getDiscount());
    }


    private static SqlParameterSource getVoucherIdSqlParameterSource(UUID voucherId) {
        return new MapSqlParameterSource()
                .addValue(TABLE_FIELD_VOUCHER_ID, voucherId);
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        final String sql = "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (:voucher_id, :voucher_type, :discount)";
        SqlParameterSource parameters = getFullSqlParametersSource(voucher);
        try { // 각 메서드에 예외를 잡아서 처리하는게 맞는지?
            return jdbcTemplate.update(sql, parameters) == 1 ? Optional.of(voucher) : Optional.empty();
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        final String sql = "SELECT * FROM vouchers WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = getVoucherIdSqlParameterSource(voucherId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameter, voucherRowMapper));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        final String sql = "SELECT * FROM vouchers";
        try {
            return jdbcTemplate.query(sql, voucherRowMapper);
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        final String sql = "UPDATE vouchers SET voucher_type = :voucher_type, discount = :discount WHERE voucher_id = :voucher_id";
        SqlParameterSource parameters = getFullSqlParametersSource(voucher);
        try {
            return jdbcTemplate.update(sql, parameters) == 1 ? Optional.of(voucher) : Optional.empty();
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(UUID voucherId) {
        final String sql = "DELETE FROM vouchers WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = getVoucherIdSqlParameterSource(voucherId);
        try {
            return jdbcTemplate.update(sql, parameter) == 1;
        } catch (DataAccessException e) {
            return false;
        }
    }

}
