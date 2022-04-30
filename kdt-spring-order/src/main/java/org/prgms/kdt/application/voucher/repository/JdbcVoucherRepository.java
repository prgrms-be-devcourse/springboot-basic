package org.prgms.kdt.application.voucher.repository;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.prgms.kdt.application.util.UuidUtils;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.exception.VoucherDuplicateKeyException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = UuidUtils.toUUID(resultSet.getBytes("voucher_id"));
        var customerId = resultSet.getBytes("customer_id") != null ? UuidUtils.toUUID(resultSet.getBytes("customer_id")) : null;
        VoucherType voucherType = VoucherType.findVoucherType(resultSet.getString("voucher_type"));
        var discountValue = resultSet.getLong("discount_value");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var updatedAt = resultSet.getTimestamp("updated_at").toLocalDateTime();
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(voucherId, customerId, discountValue, createdAt, updatedAt);
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(voucherId, customerId, discountValue, createdAt, updatedAt);
                break;
            default:
                break;
        }
        return voucher;
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
            put("customerId", voucher.getCustomerId() != null ? voucher.getCustomerId().toString().getBytes(StandardCharsets.UTF_8) : null);
            put("voucherType", voucher.getVoucherType().getType());
            put("discountValue", voucher.getDiscountValue());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("updatedAt", Timestamp.valueOf(voucher.getUpdatedAt()));
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            String sql =
                "INSERT INTO vouchers(voucher_id, customer_id, voucher_type, discount_value, created_at, updated_at) "
                    + "VALUES (UNHEX(REPLACE(:voucherId, '-', '')), UNHEX(REPLACE(:customerId, '-', '')), :voucherType, :discountValue, :createdAt, :updatedAt)";
            int updateRow = jdbcTemplate.update(sql, toParamMap(voucher));
            if (updateRow != 1) {
                throw new IllegalStateException("Voucher가 정상적으로 생성되지 않았습니다.");
            }
        } catch (DuplicateKeyException e) {
            throw new VoucherDuplicateKeyException("Voucher 중복된 키 발생", e);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes(StandardCharsets.UTF_8)),
                voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Voucher가 조회되지 않았습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        String sql = "select * from vouchers where customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        return jdbcTemplate.query(sql,
            Collections.singletonMap("customerId", customerId.toString().getBytes(StandardCharsets.UTF_8)),
            voucherRowMapper);
    }

    @Override
    public Voucher updateDiscountValue(Voucher voucher) {
        String sql = "UPDATE vouchers SET discount_value = :discountValue WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        int updateRow = jdbcTemplate.update(sql, toParamMap(voucher));
        if (updateRow != 1) {
            throw new IllegalStateException("Voucher가 정상적으로 업데이트 되지 않았습니다.");
        }
        return voucher;
    }

    @Override
    public int deleteById(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        int deleteRow = jdbcTemplate.update(sql, Collections.singletonMap("voucherId", voucherId.toString().getBytes(StandardCharsets.UTF_8)));
        if (deleteRow != 1) {
            log.error("정상적으로 삭제 되지 않았습니다.");
            throw new IllegalStateException("Voucher가 정상적으로 삭제 되지 않았습니다.");
        }
        return deleteRow;
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM vouchers";
        return jdbcTemplate.update(sql, Collections.emptyMap());
    }
}
