package com.mountain.voucherApp.adapter.out.persistence.voucher;

import com.mountain.voucherApp.application.port.out.VoucherPort;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.mountain.voucherApp.shared.constants.Field.*;
import static com.mountain.voucherApp.shared.constants.ErrorMessage.*;
import static com.mountain.voucherApp.shared.utils.CommonUtil.toUUID;

@Repository
@Profile("prod")
public class JdbcVoucherRepository implements VoucherPort {

    private static final Logger log = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<VoucherEntity> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap(VOUCHER_ID_CAMEL.getValue(), voucherId.toString().getBytes()),
                    voucherEntityRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherEntityRowMapper);
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        Map paramMap = toParamMap(voucherEntity);
        int executeUpdate = jdbcTemplate.update(
                "INSERT INTO vouchers (voucher_id, discount_policy, discount_amount) VALUES (UUID_TO_BIN(:voucherId), :discountPolicy, :discountAmount)",
                paramMap
        );
        if (executeUpdate != 1) {
            throw new RuntimeException(NOT_INSERTED);
        }
        return voucherEntity;
    }

    public VoucherEntity update(VoucherEntity voucherEntity) {
        Map paramMap = toParamMap(voucherEntity);
        int executeUpdate = jdbcTemplate.update(
                "UPDATE vouchers SET discount_policy = :discountPolicy, discount_amount = :discountAmount where voucher_id = UUID_TO_BIN(:voucherId)",
                paramMap
        );
        if (executeUpdate != 1) {
            throw new RuntimeException(NOT_UPDATED);
        }
        return voucherEntity;
    }

    @Override
    public Optional<VoucherEntity> findByDiscountPolicyAndAmount(DiscountPolicy discountPolicy, long discountAmount) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(DISCOUNT_POLICY_CAMEL.getValue(), discountPolicy.toString());
            put(DISCOUNT_AMOUNT_CAMEL.getValue(), discountAmount);
        }};
        try {
            Optional<VoucherEntity> voucherEntity = Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers WHERE discount_policy = :discountPolicy and discount_amount = :discountAmount",
                    paramMap,
                    voucherEntityRowMapper
            ));
            log.info(EXIST_VOUCHER);
            return voucherEntity;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(VOUCHER_ID_CAMEL.getValue(), voucherId.toString().getBytes(StandardCharsets.UTF_8));
        }};
        jdbcTemplate.update("DELETE from vouchers where voucher_id = UUID_TO_BIN(:voucherId)"
                ,paramMap
        );
    }

    private Map<String, Object> toParamMap(VoucherEntity voucherEntity) {
        return new HashMap<>() {{
            put(VOUCHER_ID_CAMEL.getValue(), voucherEntity.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
            put(DISCOUNT_POLICY_CAMEL.getValue(), voucherEntity.getDiscountPolicy().toString());
            put(DISCOUNT_AMOUNT_CAMEL.getValue(), voucherEntity.getDiscountAmount());
        }};
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
