package org.prgms.kdtspringweek1.voucher.repository;

import org.prgms.kdtspringweek1.exception.JdbcException;
import org.prgms.kdtspringweek1.exception.JdbcExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountValue = resultSet.getLong("discount_value");
        VoucherType voucherType = VoucherType.getVoucherTypeByName(resultSet.getString("voucher_type"));

        switch (voucherType) {
            case FIXED_AMOUNT -> {
                return FixedAmountVoucher.createWithIdAndAmount(voucherId, discountValue);
            }
            case PERCENT_DISCOUNT -> {
                return PercentDiscountVoucher.createWithIdAndPercent(voucherId, discountValue);
            }
        }
        throw new RuntimeException("행 매핑에 실패했습니다.");
    };

    private static Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountValue", voucher.getDiscountValue());
            put("voucherType", voucher.getVoucherType().getName());
        }};
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }


    @Override
    public Voucher save(Voucher voucher) {
        int isInserted = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, discount_value, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :discountValue, :voucherType)",
                toParamMap(voucher));
        if (isInserted != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_INSERT.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_INSERT);
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucherId에 해당하는 voucher를 찾지 못했습니다.");
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        int isUpdated = jdbcTemplate.update("UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        if (isUpdated != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_UPDATE.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_UPDATE);
        }

        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        int isUpdated = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));

        if (isUpdated != 1) {
            logger.error(JdbcExceptionCode.FAIL_TO_DELETE.getMessage());
            throw new JdbcException(JdbcExceptionCode.FAIL_TO_DELETE);
        }
    }
}
