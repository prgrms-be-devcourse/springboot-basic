package com.mountain.voucherApp.adapter.out.persistence.voucher;

import com.mountain.voucherApp.application.port.out.VoucherPort;
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
import static com.mountain.voucherApp.shared.constants.Message.*;
import static com.mountain.voucherApp.shared.constants.Query.*;
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

    private Map<String, Object> toParamMap(VoucherEntity voucherEntity) {
        return new HashMap<>() {{
            put(VOUCHER_ID_CAMEL, voucherEntity.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
            put(DISCOUNT_POLICY_ID_CAMEL, voucherEntity.getDiscountPolicyId());
            put(DISCOUNT_AMOUNT_CAMEL, voucherEntity.getDiscountAmount());
        }};
    }

    public Optional<VoucherEntity> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_VOUCHER_BY_ID,
                    Collections.singletonMap(VOUCHER_ID_CAMEL, voucherId.toString().getBytes()),
                    voucherEntityRowMapper
            ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT, e);
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_VOUCHER, voucherEntityRowMapper);
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        Map paramMap = toParamMap(voucherEntity);
        int executeUpdate = jdbcTemplate.update(
                INSERT_VOUCHER,
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
                UPDATE_VOUCHER,
                paramMap
        );
        if (executeUpdate != 1) {
            throw new RuntimeException(NOT_UPDATED);
        }
        return voucherEntity;
    }

    @Override
    public Optional<VoucherEntity> findByPolicyIdAndDiscountAmount(int discountPolicyId, long discountAmount) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(DISCOUNT_POLICY_ID_CAMEL, discountPolicyId);
            put(DISCOUNT_AMOUNT_CAMEL, discountAmount);
        }};
        try {
            Optional<VoucherEntity> voucherEntity = Optional.ofNullable(jdbcTemplate.queryForObject(
                    SELECT_VOUCHER_BY_POLICY_ID_AND_AMOUNT,
                    paramMap,
                    voucherEntityRowMapper
            ));
            log.info(EXIST_VOUCHER);
            return voucherEntity;
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static RowMapper<VoucherEntity> voucherEntityRowMapper = new RowMapper<VoucherEntity>() {
        @Override
        public VoucherEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            byte[] voucherId = rs.getBytes(VOUCHER_ID);
            UUID uuid = toUUID(voucherId);
            long discountAmount = rs.getLong(DISCOUNT_AMOUNT);
            int discountPolicyId = rs.getInt(DISCOUNT_POLICY_ID);
            return new VoucherEntity(uuid, discountPolicyId, discountAmount);
        }
    };

}
