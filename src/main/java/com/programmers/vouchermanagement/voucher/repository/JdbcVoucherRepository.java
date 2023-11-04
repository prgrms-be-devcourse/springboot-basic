package com.programmers.vouchermanagement.voucher.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.vouchermanagement.configuration.profiles.DBEnabledCondition;
import com.programmers.vouchermanagement.util.UUIDConverter;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@Repository
@Conditional(DBEnabledCondition.class)
public class JdbcVoucherRepository implements VoucherRepository {
    private static final int SINGLE_DATA_FLAG = 1;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> mapToVoucher(resultSet);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int savedVoucher = findById(voucher.getVoucherId()).isEmpty() ? insert(voucher) : update(voucher);
        if (savedVoucher != SINGLE_DATA_FLAG) {
            throw new NoSuchElementException("Exception is raised while saving the voucher %s".formatted(voucher.getVoucherId()));
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String findAllSQL = "SELECT * FROM vouchers";
        return Collections.unmodifiableList(namedParameterJdbcTemplate.query(findAllSQL, voucherRowMapper));
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        String findByTypeSQL = "SELECT * FROM vouchers WHERE voucher_type=(:voucherType)";
        Map<String, Object> parameterMap = Collections.singletonMap("voucherType", voucherType.name());
        return namedParameterJdbcTemplate.query(findByTypeSQL, parameterMap, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByCreatedAt(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        String findByCreatedAtSQL = "SELECT * FROM vouchers WHERE created_at BETWEEN :startDate AND :endDate";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("startDate", startDateTime);
        parameterMap.put("endDate", endDateTime);
        return namedParameterJdbcTemplate.query(findByCreatedAtSQL, Collections.unmodifiableMap(parameterMap), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String findByIdSQL = "SELECT * FROM vouchers WHERE voucher_id=UUID_TO_BIN(:voucherId)";
        Map<String, Object> parameterMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(findByIdSQL, parameterMap, voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        String findByCustomerId = "SELECT * FROM vouchers WHERE customer_id=UUID_TO_BIN(:customerId)";
        Map<String, Object> parameterMap = Collections.singletonMap("customerId", customerId.toString().getBytes());
        return namedParameterJdbcTemplate.query(findByCustomerId, parameterMap, voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        String deleteSQL = "DELETE FROM vouchers WHERE voucher_id=UUID_TO_BIN(:voucherId)";
        Map<String, Object> parameteMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        namedParameterJdbcTemplate.update(deleteSQL, parameteMap);
    }

    @Override
    @Profile("test") //clarify that this is for testing only
    public void deleteAll() {
    }

    private int insert(Voucher voucher) {
        String saveSQL = "INSERT INTO vouchers(voucher_id, created_at, discount_value, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :createdAt, :discountValue, :voucherType)";
        Map<String, Object> parameterMap = toParameterMap(voucher);
        return namedParameterJdbcTemplate.update(saveSQL, parameterMap);
    }

    private int update(Voucher voucher) {
        String updateSQL = "UPDATE vouchers SET discount_value = :discountValue, voucher_type = :voucherType WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        Map<String, Object> parameterMap = toParameterMap(voucher);
        return namedParameterJdbcTemplate.update(updateSQL, parameterMap);
    }

    private Map<String, Object> toParameterMap(Voucher voucher) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        parameterMap.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        parameterMap.put("discountValue", voucher.getDiscountValue());
        parameterMap.put("voucherType", voucher.getVoucherType().name());
        parameterMap.put("customerId", voucher.getCustomerId());

        return Collections.unmodifiableMap(parameterMap);
    }

    private static Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        final UUID voucherId = UUIDConverter.from(resultSet.getBytes("voucher_id"));
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final BigDecimal discountValue = resultSet.getBigDecimal("discount_value");
        final String voucherTypeName = resultSet.getString("voucher_type");
        final VoucherType voucherType = VoucherType.findVoucherType(voucherTypeName);
        byte[] idBytes = resultSet.getBytes("customer_id");
        final UUID customerId = idBytes != null ? UUIDConverter.from(idBytes) : null;
        return new Voucher(voucherId, createdAt, discountValue, voucherType, customerId);
    }
}
