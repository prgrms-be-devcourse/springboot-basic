package com.mountain.voucherApp.dao.voucher;

import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.model.enums.DiscountPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.mountain.voucherApp.common.constants.ErrorMessage.*;
import static com.mountain.voucherApp.common.constants.Field.*;
import static com.mountain.voucherApp.common.utils.CommonUtil.toUUID;

@Repository
@Profile("prod")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<VoucherEntity> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap(VOUCHER_ID_CAMEL.getValue(), voucherId.toString().getBytes()),
                    voucherEntityRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherEntityRowMapper);
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        int executeUpdate = jdbcTemplate.update(
                "INSERT INTO vouchers (voucher_id, discount_policy, discount_amount) VALUES (UUID_TO_BIN(:voucherId), :discountPolicy, :discountAmount)",
                toParamMap(voucherEntity)
        );
        if (executeUpdate != 1) {
            throw new RuntimeException(NOT_INSERTED.getMessage());
        }
        return voucherEntity;
    }

    public VoucherEntity update(VoucherEntity voucherEntity) {
        int executeUpdate = jdbcTemplate.update(
                "UPDATE vouchers SET discount_policy = :discountPolicy, discount_amount = :discountAmount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucherEntity)
        );
        if (executeUpdate != 1) {
            throw new RuntimeException(NOT_UPDATED.getMessage());
        }
        return voucherEntity;
    }

    @Override
    public Optional<VoucherEntity> findByDiscountPolicyAndAmount(DiscountPolicy discountPolicy, long discountAmount) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(DISCOUNT_POLICY_CAMEL.getValue(), discountPolicy.toString());
        paramMap.put(DISCOUNT_AMOUNT_CAMEL.getValue(), discountAmount);
        try {
            Optional<VoucherEntity> voucherEntity = Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE discount_policy = :discountPolicy AND discount_amount = :discountAmount",
                    paramMap,
                    voucherEntityRowMapper
            ));
            log.info(EXIST_VOUCHER.getMessage());
            return voucherEntity;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(VOUCHER_ID_CAMEL.getValue(), voucherId.toString().getBytes(StandardCharsets.UTF_8));
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)"
                , paramMap
        );
    }

    private Map<String, Object> toParamMap(VoucherEntity voucherEntity) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(VOUCHER_ID_CAMEL.getValue(), voucherEntity.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
        paramMap.put(DISCOUNT_POLICY_CAMEL.getValue(), voucherEntity.getDiscountPolicy().toString());
        paramMap.put(DISCOUNT_AMOUNT_CAMEL.getValue(), voucherEntity.getDiscountAmount());
        return paramMap;
    }

    private static RowMapper<VoucherEntity> voucherEntityRowMapper = new RowMapper<VoucherEntity>() {
        @Override
        public VoucherEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            byte[] voucherId = rs.getBytes(VOUCHER_ID.getValue());
            UUID uuid = toUUID(voucherId);
            long discountAmount = rs.getLong(DISCOUNT_AMOUNT.getValue());
            DiscountPolicy discountPolicy = DiscountPolicy.valueOf(rs.getString(DISCOUNT_POLICY.getValue()));
            return new VoucherEntity(uuid, discountPolicy, discountAmount);
        }
    };

}
