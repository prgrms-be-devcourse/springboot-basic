package com.programmers.vouchermanagement.voucher.repository;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> mapToVoucher(resultSet);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        final String saveSQL = "INSERT INTO vouchers(voucher_id, discount_value, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :discountValue, :voucherType)";
        Map<String, Object> parameterMap = toParameterMap(voucher);
        int savedVoucher = namedParameterJdbcTemplate.update(saveSQL, parameterMap);
        if (savedVoucher != 1) {
            throw new NoSuchElementException("Exception is raised while saving the voucher %s".formatted(voucher.getVoucherId()));
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        final String findAllSQL = "SELECT * FROM vouchers";
        return namedParameterJdbcTemplate.query(findAllSQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        final String findByIdSQL = "SELECT * FROM vouchers WHERE voucher_id=UUID_TO_BIN(:voucherId)";
        Map<String, Object> parameterMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(findByIdSQL, parameterMap, voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    private Map<String, Object> toParameterMap(Voucher voucher) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        parameterMap.put("discountValue", voucher.getDiscountValue());
        parameterMap.put("voucherType", voucher.getVoucherType().name());

        return Collections.unmodifiableMap(parameterMap);
    }

    private static Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        final UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        final BigDecimal discountValue = resultSet.getBigDecimal("discount_value");
        final String voucherTypeName = resultSet.getString("voucher_type");
        final VoucherType voucherType = VoucherType.findVoucherType(voucherTypeName);
        return new Voucher(voucherId, discountValue, voucherType);
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
