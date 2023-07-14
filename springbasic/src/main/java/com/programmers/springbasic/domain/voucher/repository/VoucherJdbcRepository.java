package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private static final String VOUCHER_INSERT_QUERY = "INSERT INTO vouchers(voucher_code, value, type, expiration_date, is_active, customer_id) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, UUID_TO_BIN(?))";

    private static final String VOUCHER_SELECT_ALL_QUERY = "SELECT * FROM vouchers";
    private static final String VOUCHER_SELECT_BY_TYPE_QUERY = "SELECT * FROM vouchers WHERE type = ?";
    private static final String VOUCHER_SELECT_BY_CODE_QUERY = "SELECT * FROM vouchers WHERE voucher_code = UUID_TO_BIN(?)";
    private static final String VOUCHER_SELECT_BY_CUSTOMER_ID_QUERY = "SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(?)";
    private static final String VOUCHER_SELECT_CUSTOMER_ID_BY_VOUCHER_TYPE_QUERY = "SELECT DISTINCT customer_id FROM vouchers WHERE type = ?";

    private static final String VOUCHER_UPDATE_QUERY = "UPDATE vouchers SET value = ? WHERE voucher_code = UUID_TO_BIN(?)";

    private static final String VOUCHER_DELETE_QUERY = "DELETE FROM vouchers WHERE voucher_code = UUID_TO_BIN(?)";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherCode = toUUID(resultSet.getBytes("voucher_code"));
        double value = resultSet.getDouble("value");
        String voucherType = resultSet.getString("type");
        LocalDate expirationDate = resultSet.getDate("expiration_date").toLocalDate();
        boolean isActive = resultSet.getBoolean("is_active");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));

        switch (voucherType) {
            case "FIXED_AMOUNT_VOUCHER": {
                return new FixedAmountVoucher(voucherCode, value, expirationDate, isActive, customerId);
            }
            case "PERCENT_DISCOUNT_VOUCHER": {
                return new PercentDiscountVoucher(voucherCode, value, expirationDate, isActive, customerId);
            }
            default: {
                throw new RuntimeException("조회할 voucher가 없습니다.");
            }
        }
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Voucher voucher) {
        try {
            jdbcTemplate.update(VOUCHER_INSERT_QUERY,
                    voucher.getCode().toString().getBytes(),
                    voucher.getValue(),
                    voucher.getVoucherType().getVoucherType(),
                    voucher.getExpirationDate(),
                    voucher.isActive(),
                    voucher.getCustomerId().toString().getBytes());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public Optional<Voucher> findByCode(UUID voucherCode) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(VOUCHER_SELECT_BY_CODE_QUERY,
                    voucherRowMapper,
                    voucherCode.toString().getBytes()));
        } catch (DataAccessException e) {
            logger.error(e.getMessage());

            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAllByCustomerId(UUID customerId) {
        return jdbcTemplate.query(VOUCHER_SELECT_BY_CUSTOMER_ID_QUERY,
                voucherRowMapper,
                customerId.toString().getBytes());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(VOUCHER_SELECT_ALL_QUERY, voucherRowMapper);
    }

    @Override
    public List<Voucher> findAllByVoucherType(VoucherType voucherOption) {
        String voucherType = voucherOption.getVoucherType();

        return jdbcTemplate.query(VOUCHER_SELECT_BY_TYPE_QUERY,
                voucherRowMapper,
                voucherType);
    }

    @Override
    public List<UUID> findAllCustomerIdByVoucherType(String voucherType) {
        return jdbcTemplate.query(VOUCHER_SELECT_CUSTOMER_ID_BY_VOUCHER_TYPE_QUERY,
                (resultSet, i) -> {
                    UUID customerId = toUUID(resultSet.getBytes("customer_id"));

                    return customerId;
                },
                voucherType);
    }

    @Override
    public void update(Voucher voucher) {
        try {
            jdbcTemplate.update(VOUCHER_UPDATE_QUERY,
                    voucher.getValue(),
                    voucher.getCode().toString().getBytes());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void delete(UUID voucherCode) {
        try {
            jdbcTemplate.update(VOUCHER_DELETE_QUERY,
                    voucherCode.toString().getBytes());
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
    }
}
