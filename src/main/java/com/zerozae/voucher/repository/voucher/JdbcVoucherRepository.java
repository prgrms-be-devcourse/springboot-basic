package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.util.UuidConverter;
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
    private static final String CUSTOMER_ID = "customerId";
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
        int result = jdbcTemplate.update(
                "insert into vouchers(voucher_id ,discount,voucher_type, use_status_type) values (UUID_TO_BIN(:voucherId), :discount, :voucherType, :useStatusType)",
                toParamMap(voucher));

        if(result != 1){
            throw ErrorMessage.error("저장에 실패했습니다.");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "select * from vouchers",
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher voucher = jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Map.of(VOUCHER_ID, voucherId.toString().getBytes()),
                    voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void registerVoucher(UUID customerId, UUID voucherId) {
        int result = jdbcTemplate.update(
                "update vouchers set customer_id = UUID_TO_BIN(:customerId) where voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of(
                    CUSTOMER_ID, customerId.toString().getBytes(),
                    VOUCHER_ID, voucherId.toString().getBytes()
                ));

        if(result != 1){
            throw ErrorMessage.error("바우처 등록에 실패했습니다.");
        }
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
                "select * from vouchers where customer_id = UUID_TO_BIN(:customerId)",
                Map.of(CUSTOMER_ID, customerId.toString().getBytes()),
                voucherRowMapper);
    }

    @Transactional
    @Override
    public void removeVoucher(UUID voucherId) {
        int result = jdbcTemplate.update(
                "update vouchers set customer_id = null where voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of(VOUCHER_ID, voucherId.toString().getBytes()));

        if(result != 1){
            throw ErrorMessage.error("바우처 제거에 실패했습니다.");
        }
    }

    @Override
    public Optional<UUID> findVoucherOwner(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                        "SELECT customer_id FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                        Map.of("voucherId", voucherId.toString().getBytes()),
                        byte[].class))
                .map(UuidConverter::toUUID);
    }

    @Transactional
    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(
                "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of(VOUCHER_ID, voucherId.toString().getBytes()));
    }

    @Transactional
    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcOperations().update("delete from vouchers");
    }

    @Transactional
    @Override
    public void update(UUID voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        int result = jdbcTemplate.update(
                "update vouchers set discount = :discount, use_status_type = :useStatusType where voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of(
                        DISCOUNT, voucherUpdateRequest.getDiscount(),
                        USE_STATUS_TYPE, voucherUpdateRequest.getUseStatusType().toString(),
                        VOUCHER_ID, voucherId.toString().getBytes()));

        if(result != 1){
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
