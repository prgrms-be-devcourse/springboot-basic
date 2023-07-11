package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.global.exception.ErrorMessage;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.utils.Utils;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("default")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            var updateResult = jdbcTemplate.update(
                    "INSERT INTO vouchers(voucher_id, voucher_type, discount_value) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountValue)",
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
            var updateResult = jdbcTemplate.update(
                    "UPDATE vouchers SET voucher_type = :voucherType, discount_value = :discountValue WHERE voucher_id = UUID_TO_BIN(:voucherId)",
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
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query(
                "SELECT * FROM vouchers",
                voucherRowMapper
        );
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM vouchers",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNumber) -> {
        var voucherId = Utils.toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var discountValue = DiscountValue.from(voucherType, resultSet.getDouble("discount_value"));
        return new Voucher(voucherId, voucherType, discountValue);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("voucherType", voucher.getVoucherType().toString());
        paramMap.put("discountValue", voucher.getDiscountValue().value());
        return paramMap;
    }

}
