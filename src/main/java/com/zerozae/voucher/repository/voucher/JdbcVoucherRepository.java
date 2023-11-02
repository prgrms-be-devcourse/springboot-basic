package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherCondition;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.domain.voucher.VoucherType.FIXED;
import static com.zerozae.voucher.domain.voucher.VoucherType.valueOf;
import static com.zerozae.voucher.util.UuidConverter.toUUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discount = resultSet.getLong("discount");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        UseStatusType useStatusType = UseStatusType.valueOf(resultSet.getString("use_status_type"));
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();

        if (voucherType.equals(FIXED)) {
            return new FixedDiscountVoucher(voucherId, discount, useStatusType, createdAt);
        } else {
            return new PercentDiscountVoucher(voucherId, discount, useStatusType, createdAt);
        }
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers(voucher_id ,discount,voucher_type, use_status_type, created_at) values (UUID_TO_BIN(:voucherId), :discount, :voucherType, :useStatusType, :createdAt)";
        int result = jdbcTemplate.update(
                sql,
                toParamMap(voucher));

        if(result != 1) {
            throw ExceptionMessage.error("저장에 실패했습니다.");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";
        return jdbcTemplate.query(
                sql,
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
        try {
            Voucher voucher = jdbcTemplate.queryForObject(
                    sql,
                    Map.of("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
        jdbcTemplate.update(
                sql,
                Map.of("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";
        jdbcTemplate.getJdbcOperations().update(sql);
    }

    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        String sql = "update vouchers set discount = :discount, use_status_type = :useStatusType where voucher_id = UUID_TO_BIN(:voucherId)";
        int result = jdbcTemplate.update(
                sql,
                Map.of(
                        "discount", voucherUpdateRequest.getDiscount(),
                        "useStatusType", voucherUpdateRequest.getUseStatusType().toString(),
                        "voucherId", voucherId.toString().getBytes()));

        if(result != 1) {
            throw ExceptionMessage.error("바우처 업데이트에 실패했습니다.");
        }
    }

    @Override
    public List<Voucher> findVoucherByCondition(VoucherCondition condition) {
        StringBuilder sql = new StringBuilder("select * from vouchers where ");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        if(condition.voucherType() != null) {
            if(condition.createdAt() != null) {
                sql.append("voucher_type = :voucherType and created_at = :createdAt");
                mapSqlParameterSource.addValue("voucherType", VoucherType.of(condition.voucherType()))
                                     .addValue("createdAt", LocalDate.parse(condition.createdAt()));
            } else {
                sql.append("voucher_type = :voucherType");
                mapSqlParameterSource.addValue("voucherType", VoucherType.of(condition.voucherType()));
            }
        }else {
            sql.append("created_at = :createdAt");
            mapSqlParameterSource.addValue("createdAt", condition.createdAt());
        }
        return jdbcTemplate.query(sql.toString(), mapSqlParameterSource, voucherRowMapper);
    }

    private MapSqlParameterSource toParamMap(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString().getBytes())
                .addValue("discount", voucher.getDiscount())
                .addValue("voucherType", voucher.getVoucherType().toString())
                .addValue("useStatusType", voucher.getUseStatusType().toString())
                .addValue("createdAt", voucher.getCreatedAt());
    }
}
