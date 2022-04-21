package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JdbcTemplateVoucherRepository implements VoucherRepository {
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, discount_amount) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount)";
    private static final String UPDATE_SQL = "UPDATE vouchers SET discount_amount = :discountAmount WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String FIND_ALL_SQL = "SELECT * FROM vouchers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final VoucherRowMapper voucherRowMapper = new VoucherRowMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void insert(Voucher voucher) {
        jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
    }

    @Override
    public void update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Voucher voucher;
        Map<String, byte[]> param = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        try {
            voucher = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, param, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            voucher = null;
        }
        return Optional.ofNullable(voucher);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> map = new HashMap<>();
        map.put("voucherType", voucher.getVoucherType().getOptionNumber());
        map.put("voucherId", voucher.getVoucherId().toString().getBytes());
        map.put("discountAmount", voucher.getDiscountAmount());
        return map;
    }

    private static class VoucherRowMapper implements RowMapper<Voucher> {
        private static UUID toUUID(byte[] bytes) {
            var byteBuffer = ByteBuffer.wrap(bytes);
            return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        }

        @Override
        public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            var voucherId = toUUID(resultSet.getBytes("voucher_id"));
            var voucherType = resultSet.getString("voucher_type");
            int discountAmount = resultSet.getInt("discount_amount");
            return new Voucher(voucherId, voucherType, discountAmount);
        }
    }
}
