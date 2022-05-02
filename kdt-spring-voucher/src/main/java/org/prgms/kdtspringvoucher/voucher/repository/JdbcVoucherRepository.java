package org.prgms.kdtspringvoucher.voucher.repository;

import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("dev")
@Transactional(readOnly = true)
public class JdbcVoucherRepository implements VoucherRepository{

    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Voucher save(Voucher voucher) {
        int save = jdbcTemplate.update(
                "insert into vouchers(voucher_id,customer_id, amount, voucher_type,created_at) values (UUID_TO_BIN(:voucherId),UUID_TO_BIN(:customerId),:amount,:voucherType,:createdAt)",
                toVoucherParamMap(voucher));
        if (save != 1) {
            throw new RuntimeException("Nothing was saved");
        }
        return voucher;
    }

    @Override
    @Transactional
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(
                "update vouchers set customer_id = UUID_TO_BIN(:customerId), amount = :amount where voucher_id = UUID_TO_BIN(:voucherId)",
                toVoucherParamMap(voucher));
        if (update != 1){
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    getVoucherRowMapper()));
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got empty result", exception);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query(
                    "select * from vouchers where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    getVoucherRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got empty result", exception);
            return null;
        }
    }

    @Override
    public List<Voucher> findByParam(VoucherType voucherType, LocalDateTime from, LocalDateTime to) {
        StringBuilder stringBuilder = new StringBuilder("select * from vouchers ");
        boolean isPresent = false;
        if (voucherType != null || from != null || to != null) {
            stringBuilder.append("where ");
            if (voucherType != null) {
                stringBuilder.append("voucher_type = :voucherType ");
                isPresent = true;
            }
            if (from != null) {
                if (isPresent) {
                    stringBuilder.append("and ");
                }
                stringBuilder.append("created_at >= :from ");
                isPresent = true;
            }
            if (to != null) {
                if (isPresent) {
                    stringBuilder.append("and ");
                }
                stringBuilder.append("created_at <= :to ");
            }
        }
        String sql = stringBuilder.toString();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherType", voucherType != null ? voucherType.toString() : null);
        paramMap.put("from", from != null ? from : null);
        paramMap.put("to", to != null ? to : null);
        logger.info("sql = {}", sql);
        try {
            return jdbcTemplate.query(
                    sql,
                    paramMap,
                    getVoucherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", getVoucherRowMapper());
    }

    @Override
    @Transactional
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers", Collections.emptyMap());
    }

    @Override
    @Transactional
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(
                "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    @Transactional
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update(
                "delete from vouchers where customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }



    private Map<String, Object> toVoucherParamMap(Voucher voucher) {
        Map<String, Object> voucherParamMap = new HashMap<>();
        voucherParamMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        voucherParamMap.put("customerId", voucher.getCustomerId() != null ? voucher.getCustomerId().toString().getBytes() : null);
        voucherParamMap.put("amount", voucher.getAmount());
        voucherParamMap.put("voucherType",voucher.getVoucherType().toString());
        voucherParamMap.put("createdAt",voucher.getCreatedAt());
        return voucherParamMap;
    }

    private RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, i) -> {
            UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
            UUID customerId = resultSet.getBytes("customer_id") != null ? toUUID(resultSet.getBytes("customer_id")) : null;
            Long amount = resultSet.getLong("amount");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return createVoucherFromDB(voucherId,customerId, amount, voucherType, createdAt);
        };
    }

    private Voucher createVoucherFromDB(UUID voucherId, UUID customerId, Long amount, VoucherType voucherType, LocalDateTime createdAt) {
        if (voucherType == VoucherType.FIXED) {
            return new FixedAmountVoucher(voucherId,customerId, amount, voucherType, createdAt);
        }
        if (voucherType == VoucherType.PERCENT) {
            return new PercentDiscountVoucher(voucherId,customerId, amount, voucherType, createdAt);
        }
        return null;
    }

    private UUID toUUID(byte[] voucherId) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(voucherId);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
