package com.programmers.VoucherManagementApplication.repository;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("voucher_type"); //fixed_discount vs fixed
        long amount = resultSet.getLong("amount");
        return Voucher.from(voucherId, VoucherType.getName(type), new Amount(amount));
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new ConcurrentHashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", String.valueOf(voucher.getVoucherType()));
            put("amount", voucher.getAmount().getAmount());
            put("usageStatus", voucher.getUsageStatus());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, amount) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :amount)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET voucher_type = :voucherType, amount = :amount, usage_status = :usageStatus WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM vouchers", Collections.emptyMap(), Integer.class);
    }
}
