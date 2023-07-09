package com.programmers.application.repository.voucher;

import com.programmers.application.domain.voucher.FixedAmountVoucher;
import com.programmers.application.domain.voucher.PercentDiscountVoucher;
import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO voucher (voucher_id, discount_amount, type) VALUES (:voucherId, :discountAmount, :type)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("voucherId", toBytes(voucher.getVoucherId()))
                .addValue("discountAmount", voucher.getDiscountAmount())
                .addValue("type", voucher.getVoucherType().name());
        namedParameterJdbcTemplate.update(sql, params);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM voucher";
        return namedParameterJdbcTemplate.query(sql, getVoucherRowMapper());
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM voucher WHERE voucher_id = :voucherId";
        HashMap<String, Object> param = new HashMap<>();
        param.put("voucherId", toBytes(voucherId));
        Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, param, getVoucherRowMapper());
        return Optional.ofNullable(voucher);
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private byte[] toBytes(UUID voucherId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(voucherId.getMostSignificantBits());
        byteBuffer.putLong(voucherId.getLeastSignificantBits());
        return byteBuffer.array();
    }

    private static RowMapper<Voucher> getVoucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            long discountAmount = rs.getInt("discount_amount");
            String type = rs.getString("type");
            VoucherType voucherType = VoucherType.valueOf(type.toUpperCase());
            return switch (voucherType) {
                case FIXED -> FixedAmountVoucher.of(voucherId, discountAmount);
                case PERCENT -> PercentDiscountVoucher.of(voucherId, discountAmount);
            };
        };
    }
}
