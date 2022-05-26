package org.programmers.springbootbasic.application.voucher.repository;


import org.programmers.springbootbasic.core.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.core.exception.NotInsertException;
import org.programmers.springbootbasic.core.exception.NotUpdateException;
import org.programmers.springbootbasic.application.voucher.model.Voucher;
import org.programmers.springbootbasic.application.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final int SUCCESS = 1;
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("결과 값이 없습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    public List<Voucher> findByCreatedAt() {
        return jdbcTemplate.query("SELECT * FROM vouchers ORDER BY created_at", voucherRowMapper);
    }

    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_type = :voucherType",
                Collections.singletonMap("voucherType", voucherType.toString()),
                voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) throws DuplicateObjectKeyException {
        Map<String, ?> parameterMap = Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "discountValue", voucher.getValue(),
                "createdAt", Timestamp.valueOf(voucher.getCreatedAt()),
                "voucherType", voucher.getVoucherType().name());
        var insert = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, discount_value, created_at, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :discountValue, :createdAt, :voucherType)",
                parameterMap);
        if (insert != SUCCESS) {
            throw new NotInsertException("Nothing was inserted");
        }

        return voucher;
    }

    public Voucher update(Voucher voucher) {
        Map<String, ?> parameterMap = Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "discountValue", voucher.getValue());

        var update = jdbcTemplate.update(
                "UPDATE vouchers SET discount_value = :discountValue WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                parameterMap);
        if (update != SUCCESS) {
            throw new NotUpdateException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> parameterMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                parameterMap);
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public int getCountByVoucherId(UUID voucherId) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()), Integer.class);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var discountValue = resultSet.getLong("discount_value");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var voucherType = resultSet.getString("voucher_type");

        return VoucherType.findByType(voucherType).create(voucherId, discountValue, createdAt);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
