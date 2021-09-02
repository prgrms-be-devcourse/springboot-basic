package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.common.util.MapConverter;
import org.prgrms.kdt.kdtspringorder.common.util.UuidUtil;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Profile({"dev", "default"})
@Repository
public class VoucherJdbcRepository implements VoucherRepository{

    private final String SELECT_ALL_SQL = "select * from vouchers";
    private final String SELECT_BY_ID_SQL = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String INSERT_SQL = "UPDATE vouchers SET discount = :name, email = :email, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
//    private final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, customer_id, discount_percent, discount_amount) VALUES(UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), :percent, :amount)";
    private final String UPDATE_DISCOUNT_SQL = "UPDATE vouchers SET discount = :discount WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private final String DELETE_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId) ";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, VoucherJdbcRepository::voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString()), VoucherJdbcRepository::voucherRowMapper));
    }

    @Override
    public UUID insert(Voucher voucher) {
        final Map<String, Object> paramMap = MapConverter.toParamMap(voucher);

        String discountFieldName = voucher.getVoucherType().equals(VoucherType.FIX) ? "amount" : "percent";
        paramMap.put("voucherType", voucher.getVoucherType().getValue());

        final String dynamicInsertSql = MessageFormat.format(INSERT_SQL, discountFieldName);
        final int update = jdbcTemplate.update(dynamicInsertSql, paramMap);
        if (update != 1) {
            throw new VoucherNotFoundException(voucher.getVoucherId());
        }
        return voucher.getVoucherId();
    }

    @Override
    public UUID updateDiscount(Voucher voucher) {
        final Map<String, Object> paramMap = MapConverter.toParamMap(voucher);

        String discountFieldName = voucher.getVoucherType().equals(VoucherType.FIX) ? "amount" : "percent";
        paramMap.put("voucherType", voucher.getVoucherType().getValue());
        final String dynamicUpdateSql = MessageFormat.format(UPDATE_DISCOUNT_SQL, discountFieldName);

        final int updateColumnCnt = jdbcTemplate.update(dynamicUpdateSql, paramMap);
        if (updateColumnCnt != 1) {
            throw new VoucherNotFoundException(voucher.getVoucherId());
        }
        return voucher.getVoucherId();
    }

    @Override
    public int delete(UUID voucherId) {
        final int deleteColumnCnt = jdbcTemplate.update(DELETE_SQL, Map.of("voucherId", voucherId));
        if (deleteColumnCnt != 1) {
            throw new VoucherNotFoundException(voucherId);
        }
        return deleteColumnCnt;
    }

    private static Voucher voucherRowMapper(ResultSet resultSet, int i) throws SQLException {
        final UUID voucherId = UuidUtil.toUUID(resultSet.getBytes("voucher_id"));
        final UUID customerId = resultSet.getBytes("customer_id") != null ? UuidUtil.toUUID(resultSet.getBytes("customer_id")) : null;
        final String useYn = resultSet.getString("use_yn");
        final String voucherType = resultSet.getString("voucher_type");
        Long discount = resultSet.getLong("discount");
        final LocalDateTime usedAt = resultSet.getTimestamp("used_at") != null ? resultSet.getTimestamp("used_at").toLocalDateTime() : null;
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at") != null ? resultSet.getTimestamp("created_at").toLocalDateTime() : null;

        return VoucherType.findVoucherType(voucherType).createVoucher(voucherId, customerId, discount, useYn, createdAt, usedAt);
    }

}
