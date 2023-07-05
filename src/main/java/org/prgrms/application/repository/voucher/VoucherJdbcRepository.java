package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.*;
import org.prgrms.application.entity.VoucherEntity;
import org.prgrms.application.repository.customer.CustomerJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private static final int HAS_UPDATE = 1;
    private static final String VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String DISCOUNT_AMOUNT = "dicount_amount";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // db로부터 정보를 가져옴
    private static final RowMapper<VoucherEntity> voucherRowMapper = (resultSet, i) -> {
        Long voucherId = resultSet.getLong(VOUCHER_ID);
        String voucherType= resultSet.getString(VOUCHER_TYPE);
        Double discountAmount = resultSet.getDouble(DISCOUNT_AMOUNT);
        VoucherEntity voucherEntity = new VoucherEntity(voucherId, voucherType, discountAmount);
        return voucherEntity;
    };

    // 맵의 키를 파라미터로 변경
    private Map<String, Object> toParamMap(VoucherEntity voucherEntity) {
        return new HashMap<>() {{
            put(VOUCHER_ID, voucherEntity.getVoucherId());
            put(VOUCHER_TYPE, voucherEntity.getVoucherType());
            put(DISCOUNT_AMOUNT, voucherEntity.getDiscountAmount());
        }};
    }

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, fixed_amount, percent_amount) " +
                        "VALUES (:voucherId, :voucherType, :fixedAmount, :percentAmount)",
                toParamMap(voucherEntity));
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucherEntity;
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity) {
        int update = jdbcTemplate.update("UPDATE vouchers SET fixed_amount = :fixedAmount, percent_amount = :percentAmount WHERE voucher_id = :voucherId",
                toParamMap(voucherEntity));
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucherEntity;
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Optional<VoucherEntity> findById(Long voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = :voucherId",
                    Collections.singletonMap(VOUCHER_ID, voucherId),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherEntity> findByType(VoucherType voucherType) {
        List<VoucherEntity> vouchers = jdbcTemplate.query("select * from vouchers WHERE voucher_type = :voucherType",
                Collections.singletonMap(VOUCHER_TYPE, voucherType.name()),
                voucherRowMapper);
        return vouchers;

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
