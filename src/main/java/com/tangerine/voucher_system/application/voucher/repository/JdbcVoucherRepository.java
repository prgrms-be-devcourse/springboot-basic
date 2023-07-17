package com.tangerine.voucher_system.application.voucher.repository;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"default", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNumber) -> {
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        DiscountValue discountValue = new DiscountValue(voucherType, resultSet.getDouble("discount_value"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        String customerId = resultSet.getString("customer_id");
        if (customerId == null) {
            return new Voucher(voucherId, voucherType, discountValue, createdAt, Optional.empty());
        }
        return new Voucher(voucherId, voucherType, discountValue, createdAt, Optional.of(UUID.fromString(customerId)));
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            int updateResult = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_id, voucher_type, discount_value, created_at)" +
                            " VALUES (:voucherId, :voucherType, :discountValue, :createdAt)",
                    toParamMap(voucher)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
            return voucher;
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        try {
            int updateResult = jdbcTemplate.update(
                    "UPDATE vouchers SET voucher_type = :voucherType, discount_value = :discountValue, created_at = :createdAt WHERE voucher_id = :voucherId",
                    toParamMap(voucher)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_UPDATE.getMessageText());
            }
            return voucher;
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query(
                    "SELECT voucher_id, voucher_type, discount_value, created_at, customer_id FROM vouchers",
                    voucherRowMapper
            );
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT voucher_id, voucher_type, discount_value, created_at, customer_id FROM vouchers WHERE voucher_id = :voucherId",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByVoucherType(VoucherType voucherType) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT voucher_id, voucher_type, discount_value, created_at, customer_id FROM vouchers WHERE voucher_type = :voucherType",
                            Collections.singletonMap("voucherType", voucherType.toString()),
                            voucherRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> findByCreatedAt(LocalDateTime createdAt) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT voucher_id, voucher_type, discount_value, created_at, customer_id FROM vouchers WHERE created_at = :createdAt",
                            Collections.singletonMap("createdAt", createdAt.toString()),
                            voucherRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update(
                    "DELETE FROM vouchers",
                    Collections.emptyMap()
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM vouchers WHERE voucher_id = :voucherId",
                    Collections.singletonMap("voucherId", voucherId.toString())
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString());
        paramMap.put("voucherType", voucher.getVoucherType().toString());
        paramMap.put("discountValue", voucher.getDiscountValue().getValue());
        paramMap.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        return paramMap;
    }

}
