package com.programmers.voucher.repository;

import com.programmers.voucher.domain.TypeOfVoucher;
import com.programmers.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Qualifier("Jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        UUID voucherId = toUUID(rs.getBytes("voucher_id"));
        TypeOfVoucher type = TypeOfVoucher.getType(rs.getString("type"));
        int discount = rs.getInt("discount");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime expiredAt = rs.getTimestamp("expired_at").toLocalDateTime();
        String customerEmail = rs.getString("customer_email");
        return new Voucher(voucherId, type, discount, createdAt, expiredAt, customerEmail);
    };

    @Override
    public void insert(Voucher voucher) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type", TypeOfVoucher.getType(voucher.getTypeOfVoucher()));
            put("discount", voucher.getDiscount());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("expiredAt", Timestamp.valueOf(voucher.getExpiredAt()));
            put("customerEmail", voucher.getCustomerEmail());
        }};
        int update = namedParameterJdbcTemplate.update("INSERT INTO voucher(voucher_id, type, discount, created_at, expired_at, customer_email) VALUES(UUID_TO_BIN(:voucherId), :type, :discount, :createdAt, :expiredAt, :customerEmail)", paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM voucher", rowMapper);
    }

    @Override
    public List<Voucher> findByCustomerEmail(String customerEmail) {
        return namedParameterJdbcTemplate.query("SELECT * FROM voucher WHERE customer_email= :customerEmail", Collections.singletonMap("customerEmail", customerEmail),rowMapper);
    }

    @Override
    public List<Voucher> findByDate(String customerEmail, LocalDateTime date) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("customerEmail", customerEmail);
            put("date", Timestamp.valueOf(date));
        }};
        return namedParameterJdbcTemplate.query("SELECT * FROM voucher WHERE customer_email= :customerEmail AND DATE(created_at)= :date", paramMap, rowMapper);
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id=UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()), rowMapper));
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }

    @Override
    public void update(UUID voucherId, long discount) {
        Map<String, ?> paramMap = new HashMap<>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("discount", discount);
        }};
        int update = namedParameterJdbcTemplate.update("UPDATE voucher SET discount= :discount WHERE voucher_id=UUID_TO_BIN(:voucherId)", paramMap);
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        int update = namedParameterJdbcTemplate.update("DELETE FROM voucher WHERE voucher_id=UUID_TO_BIN(:voucherId)", Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }
}
