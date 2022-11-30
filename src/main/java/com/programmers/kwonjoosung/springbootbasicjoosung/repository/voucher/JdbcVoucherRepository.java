package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;

import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataAlreadyExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataNotExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherFactory;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherTable.*;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherTable.DISCOUNT;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherTable.VOUCHER_TYPE;

/* 예외 처리 및 Logging에 대해서 다시 생각해 보자!
 * AOP를 적용할 수도 있고, CustomException을 어떻게 활용할지..
 * SQL 명령어를 따로 빼는게 나을지?
 */
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public static final String VOUCHER = "voucher";
    private static final int FAIL = 0;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> VoucherFactory.createVoucher(
            VoucherType.of(rs.getString(VOUCHER_TYPE.getColumnName())),
            UUID.fromString(rs.getString(VOUCHER_ID.getColumnName())),
            rs.getLong(DISCOUNT.getColumnName()));

    private static SqlParameterSource getFullSqlParametersSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue(VOUCHER_ID.getColumnName(), voucher.getVoucherId().toString())
                .addValue(VOUCHER_TYPE.getColumnName(), voucher.getVoucherType().toString())
                .addValue(DISCOUNT.getColumnName(), voucher.getDiscount());
    }

    private static SqlParameterSource getVoucherIdSqlParameterSource(UUID voucherId) {
        return new MapSqlParameterSource()
                .addValue(VOUCHER_ID.getColumnName(), voucherId.toString());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        final String sql = "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (:voucher_id, :voucher_type, :discount)";
        SqlParameterSource parameters = getFullSqlParametersSource(voucher);
        try {
            jdbcTemplate.update(sql, parameters); // FAIL이 나올 수 있나?
            return voucher;
        } catch (DuplicateKeyException e) {
            throw new DataAlreadyExistException(voucher.getVoucherId().toString(), VOUCHER);
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        final String sql = "SELECT * FROM vouchers WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = getVoucherIdSqlParameterSource(voucherId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameter, voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // 이렇게 굳이 하는 이유가 무엇일까? 예외를 던져도 되지 않나?
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        final String sql = "SELECT * FROM vouchers WHERE voucher_type = :voucher_type";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(VOUCHER_TYPE.getColumnName(), voucherType.toString());
        try {
            return jdbcTemplate.query(sql, parameter, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Voucher> findAll() {
        final String sql = "SELECT * FROM vouchers";
        try {
            return jdbcTemplate.query(sql, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        final String sql = "UPDATE vouchers SET voucher_type = :voucher_type, discount = :discount WHERE voucher_id = :voucher_id";
        SqlParameterSource parameters = getFullSqlParametersSource(voucher);
        if(jdbcTemplate.update(sql, parameters) == FAIL) {
            throw new DataNotExistException(voucher.getVoucherId().toString(), VOUCHER);
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        final String sql = "DELETE FROM vouchers WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = getVoucherIdSqlParameterSource(voucherId);
        if(jdbcTemplate.update(sql, parameter) == FAIL) {
            throw new DataNotExistException(voucherId.toString(), VOUCHER);
        }
    }

}
