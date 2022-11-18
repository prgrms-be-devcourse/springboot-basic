package com.programmers.voucher.repository.voucher;

import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final String insertSql
            = "INSERT INTO vouchers(voucher_id, discount_value, voucher_type) " +
            "VALUES(UUID_TO_BIN(:voucherId), :discountValue, :voucherType)";
    private static final String findAllSql = "SELECT * FROM vouchers";
    private static final String findSql
            = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String updateSql
            = "UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType " +
            "WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String deleteSql = "DELETE FROM vouchers";

    private static final RowMapper<Voucher> rowMapper = (resultSet, count) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountValue = resultSet.getLong("discount_value");
        String voucherType = resultSet.getString("voucher_type");
        return VoucherType.toVoucherType(voucherType).convertToVoucher(voucherId, discountValue);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Voucher save(Voucher voucher, VoucherType voucherType) {
        jdbcTemplate.update(insertSql, toParamMap(voucher, voucherType));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(findAllSql, rowMapper);
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return jdbcTemplate.queryForObject(findSql, toIdMap(voucherId), rowMapper);
    }

    @Override
    public void update(Voucher voucher, VoucherType voucherType) {
        jdbcTemplate.update(updateSql, toParamMap(voucher, voucherType));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(deleteSql, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher, VoucherType voucherType) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "discountValue", voucher.getDiscountValue(),
                "voucherType", voucherType.getVoucherType()
        );
    }

    private Map<String, Object> toIdMap(UUID voucherId) {
        return Map.of(
                "voucherId", voucherId.toString().getBytes()
        );
    }
}
