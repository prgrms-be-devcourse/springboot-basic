package com.tangerine.voucher_system.application.wallet.repository;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"default", "test"})
public class JdbcWalletRepository implements WalletRepository {

    static RowMapper<Voucher> voucherRowMapper = (resultSet, rowNumber) -> {
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

    static RowMapper<Customer> customerRowMapper = (resultSet, rowNumber) -> {
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        String name = resultSet.getString("name");
        boolean isBlack = resultSet.getBoolean("black");
        return new Customer(customerId, name, isBlack);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateVoucher(UUID voucherId, UUID customerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("voucherId", voucherId.toString());
            paramMap.put("customerId", customerId.toString());
            var updateResult = jdbcTemplate.update(
                    "UPDATE vouchers SET customer_id = :customerId WHERE voucher_id = :voucherId",
                    paramMap
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public void updateVoucher(UUID voucherId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("voucherId", voucherId.toString());
            var updateResult = jdbcTemplate.update(
                    "UPDATE vouchers SET customer_id = null WHERE voucher_id = :voucherId",
                    paramMap
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e.getCause());
        }
    }

    @Override
    public List<Voucher> findAllVouchersByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query(
                    "SELECT voucher_id, voucher_type, discount_value, created_at, customer_id FROM vouchers WHERE customer_id = :customerId",
                    Collections.singletonMap("customerId", customerId.toString()),
                    voucherRowMapper
            );
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT customer_id, name, black FROM customers " +
                                    "WHERE customer_id = (SELECT customer_id FROM vouchers WHERE voucher_id = :voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString()),
                            customerRowMapper
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAllCustomersByVoucherType(VoucherType voucherType) {
        try {
            return jdbcTemplate.query(
                    "SELECT customer_id, name, black FROM customers WHERE customer_id IN (SELECT customer_id FROM vouchers WHERE voucher_type = :voucherType)",
                    Collections.singletonMap("voucherType", voucherType.toString()),
                    customerRowMapper
            );
        } catch (DataAccessException e) {
            return List.of();
        }
    }

}
