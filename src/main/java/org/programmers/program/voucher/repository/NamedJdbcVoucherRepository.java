package org.programmers.program.voucher.repository;

import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Primary
public class NamedJdbcVoucherRepository implements VoucherRepository{
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public NamedJdbcVoucherRepository(NamedParameterJdbcTemplate namedJdbcTemplate){
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = resultSet.getString("voucher_type");
        var discountAmount = resultSet.getLong("discount_amount");
        // var isUsed = resultSet.getBoolean("is_used");
        // var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var expirationDate = resultSet.getTimestamp("expiration_date").toLocalDateTime();

        if (voucherType.equals(VoucherType.PERCENT.toString()))
            return new PercentDiscountVoucher(voucherId, discountAmount, expirationDate);
        return new FixedAmountVoucher(voucherId, discountAmount, expirationDate);
    };

    private Map<String, Object> toParamMap(Voucher voucher){
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType().toString());
            put("discountAmount", voucher.getDiscountAmount());
            put("isUsed", voucher.getIsUsed());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("expirationDate", Timestamp.valueOf(voucher.getExpirationDate()));
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var updateState = namedJdbcTemplate.update(
                "insert into vouchers(voucher_id, voucher_type, discount_amount, is_used, created_at, expiration_date) values " +
                        " (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount, :isUsed, :createdAt, :expirationDate)",
                toParamMap(voucher)
        );
        if (updateState != 1)
            throw new RuntimeException("Failed to insert");
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    namedJdbcTemplate.queryForObject(
                            "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper
                    )
            );
        }catch (EmptyResultDataAccessException e){
            // logger.error("got empty result,", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return namedJdbcTemplate.query("select * from vouchers;", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        namedJdbcTemplate.update("delete from vouchers", Collections.emptyMap());
    }

    @Override
    public int count() {
        return namedJdbcTemplate.queryForObject("select count(*) from vouchers", Collections.emptyMap(),Integer.class);
    }

    static UUID toUUID(byte[]  bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
