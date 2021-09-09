package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.common.util.UuidUtil;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Profile({"dev", "default"})
@Repository
public class VoucherJdbcRepository implements VoucherRepository{

    private final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String SELECT_BY_CUSTOMER_ID_SQL = "SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, customer_id, discount, voucher_type) VALUES(UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), :discount, :voucherType)";
    private final String UPDATE_DISCOUNT_SQL = "UPDATE vouchers SET discount = :discount WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String UPDATE_CUSTOMER_ID_SQL = "UPDATE vouchers SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String DELETE_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId) ";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, VoucherJdbcRepository::voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    SELECT_BY_ID_SQL,
                    Collections.singletonMap("voucherId", voucherId.toString()),
                    VoucherJdbcRepository::voucherRowMapper
                )
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
            SELECT_BY_CUSTOMER_ID_SQL,
            Collections.singletonMap("customerId", customerId.toString()),
            VoucherJdbcRepository::voucherRowMapper
        );
    }

    @Override
    public UUID insert(Voucher voucher) {
        final Map<String, Object> paramMap = toParamMap(voucher);

        final int update = jdbcTemplate.update(INSERT_SQL, paramMap);
        if (update != 1) {
            throw new VoucherNotFoundException(voucher.getVoucherId());
        }
        return voucher.getVoucherId();
    }

    @Override
    public UUID updateDiscount(UUID voucherId, long amount) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucherId.toString());
        paramMap.put("discount", amount);

        final int updateColumnCnt = jdbcTemplate.update(UPDATE_DISCOUNT_SQL, paramMap);
        if (updateColumnCnt != 1) {
            throw new VoucherNotFoundException(voucherId);
        }
        return voucherId;
    }

    @Override
    public UUID updateCustomerId(UUID voucherId, UUID customerId) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucherId.toString());
        paramMap.put("customerId", customerId.toString());

        final int updateColumnCnt = jdbcTemplate.update(UPDATE_CUSTOMER_ID_SQL, paramMap);
        if (updateColumnCnt != 1) {
            throw new VoucherNotFoundException(voucherId);
        }
        return voucherId;
    }

    @Override
    public int delete(UUID voucherId) {
        final int deleteColumnCnt = jdbcTemplate.update(DELETE_SQL, Map.of("voucherId", voucherId.toString()));
        if (deleteColumnCnt != 1) {
            throw new VoucherNotFoundException(voucherId);
        }
        return deleteColumnCnt;
    }

    private static Voucher voucherRowMapper(ResultSet resultSet, int i) throws SQLException {
        final UUID voucherId = UuidUtil.toUUID(resultSet.getBytes("voucher_id"));
        final UUID customerId = resultSet.getBytes("customer_id") != null
            ? UuidUtil.toUUID(resultSet.getBytes("customer_id")) : null;
        final boolean useYn = resultSet.getBoolean("use_yn");
        final String voucherType = resultSet.getString("voucher_type");
        Long discount = resultSet.getLong("discount");
        final LocalDateTime usedAt = resultSet.getTimestamp("used_at") != null
            ? resultSet.getTimestamp("used_at").toLocalDateTime() : null;
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at") != null
            ? resultSet.getTimestamp("created_at").toLocalDateTime() : null;

        return VoucherType.findVoucherType(voucherType).createVoucher(voucherId, customerId, discount, useYn, createdAt, usedAt);
    }

    private HashMap<String, Object> toParamMap(Voucher voucher) {

        String voucherId = voucher.getVoucherId().toString();
        String customerId = voucher.getCustomerId() != null ? voucher.getCustomerId().toString() : null;
        String voucherType = voucher.getVoucherType().getValue();
        boolean useYn = voucher.getUseYn();
        Long discount = voucher.getAmount();
        Timestamp createdAt = voucher.getCreatedAt() != null ? Timestamp.valueOf(voucher.getCreatedAt()) : null;
        Timestamp usedAt = voucher.getUsedAt() != null ? Timestamp.valueOf(voucher.getUsedAt()) : null;

        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucherId);
        paramMap.put("customerId", customerId);
        paramMap.put("voucherType", voucherType);
        paramMap.put("useYn", useYn);
        paramMap.put("discount", discount);
        paramMap.put("createdAt", createdAt);
        paramMap.put("usedAt", usedAt);

        return paramMap;
    }

}
