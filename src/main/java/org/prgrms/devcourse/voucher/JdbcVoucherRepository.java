package org.prgrms.devcourse.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
        var voucherId = toUUID(rs.getBytes("voucher_id"));
        var discountValue = rs.getLong("discount_value");
        var voucherType =  VoucherType.findByString(rs.getString("voucher_type"));
        return Voucher.of(voucherId, discountValue, voucherType);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("insert into voucher(voucher_id, discount_value, voucher_type) values(UUID_TO_BIN(:voucherId), :discountValue, :voucherType)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update("update voucher set discount_value = :discountValue, voucher_type = :voucherType where voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from voucher where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("delete from voucher");
    }

    private HashMap<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountValue", voucher.getDiscountValue());
            put("voucherType", voucher.getVoucherType().toString());
        }};
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
