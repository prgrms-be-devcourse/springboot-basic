package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.domain.voucher.VoucherType.FIXED;
import static com.zerozae.voucher.util.UuidConverter.toUUID;

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String VOUCHER_ID = "voucherId";
    private static final String DISCOUNT = "discount";
    private static final String VOUCHER_TYPE = "voucherType";
    private static final String USE_STATUS_TYPE = "useStatusType";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discount = resultSet.getLong(DISCOUNT);
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        UseStatusType useStatusType = UseStatusType.valueOf(resultSet.getString("use_status_type"));

        if (voucherType.equals(FIXED)) {
            return new FixedDiscountVoucher(voucherId, discount, useStatusType);
        } else {
            return new PercentDiscountVoucher(voucherId, discount, useStatusType);
        }
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers(voucher_id ,discount,voucher_type, use_status_type) values (UUID_TO_BIN(:voucherId), :discount, :voucherType, :useStatusType)";
        int result = jdbcTemplate.update(
                sql,
                toParamMap(voucher));

        if(result != 1){
            throw ErrorMessage.error("저장에 실패했습니다.");
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
                    Map.of(VOUCHER_ID, voucherId.toString().getBytes()),
                    voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
        jdbcTemplate.update(
                sql,
                Map.of(VOUCHER_ID, voucherId.toString().getBytes()));
    }

    @Transactional
    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";
        jdbcTemplate.getJdbcOperations().update(sql);
    }

    @Transactional
    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        String sql = "update vouchers set discount = :discount, use_status_type = :useStatusType where voucher_id = UUID_TO_BIN(:voucherId)";
        int result = jdbcTemplate.update(
                sql,
                Map.of(
                        DISCOUNT, voucherUpdateRequest.getDiscount(),
                        USE_STATUS_TYPE, voucherUpdateRequest.getUseStatusType().toString(),
                        VOUCHER_ID, voucherId.toString().getBytes()));

        if(result != 1) {
            throw ErrorMessage.error("바우처 업데이트에 실패했습니다.");
        }
    }
    private MapSqlParameterSource toParamMap(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue(VOUCHER_ID, voucher.getVoucherId().toString().getBytes())
                .addValue(DISCOUNT, voucher.getDiscount())
                .addValue(VOUCHER_TYPE, voucher.getVoucherType().toString())
                .addValue(USE_STATUS_TYPE, voucher.getUseStatusType().toString());
    }
}