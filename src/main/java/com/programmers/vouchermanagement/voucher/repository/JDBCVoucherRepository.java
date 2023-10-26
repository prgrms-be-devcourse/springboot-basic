package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Primary
public class JDBCVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JDBCVoucherRepository.class);
    private static final String INSERT_QUERY = "INSERT INTO test.vouchers(id, type, discount_value) VALUES (UUID_TO_BIN(:id), :type, :discountValue)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM test.vouchers WHERE id = UUID_TO_BIN(:id)";
    private static final String FIND_ALL_QUERY = "SELECT * FROM test.vouchers";
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        BigDecimal discountValue = resultSet.getBigDecimal("discount_value");
        String voucherTypeStr = resultSet.getString("type");

        return new Voucher(id, discountValue, VoucherType.valueOf(voucherTypeStr));
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JDBCVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", voucher.getVoucherId().toString().getBytes());
        paramMap.put("type", voucher.getVoucherType().name());
        paramMap.put("discountValue", voucher.getDiscountValue());
        return paramMap;
    }

    @Override
    public void save(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_QUERY, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, voucherRowMapper);
    }

    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_QUERY,
                    Collections.singletonMap("id", id.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }
}
