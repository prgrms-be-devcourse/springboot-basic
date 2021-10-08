package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"dev", "test"})
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private static final int SUCCESS = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long amount = resultSet.getLong("discount_amount");
        String voucherType = resultSet.getString("voucher_type");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        String customerEmail = resultSet.getString("customer_email");

        return VoucherFactory.create(voucherType, voucherId, amount);
    };

    private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        final var customerName = resultSet.getString("customer_name");
        final var customerEmail = resultSet.getString("customer_email");
        final var customerId = toUUID(resultSet.getBytes("customer_id"));
        final var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, customerName, customerEmail, createdAt, lastLoginAt);
    };

    public JdbcVoucherRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID toUUID(final byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(final Voucher voucher) {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("DiscountAmount", voucher.getVoucherDiscount());
        paramMap.put("voucherType", voucher.getVoucherType());

        return paramMap;
    }

    @Override
    public Optional<Voucher> findById(final UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper));
        } catch (final EmptyResultDataAccessException e) {
            logger.error("데이터가 존재하지 않습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(final Voucher voucher) {
        final int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, voucher_type, discount_amount) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :DiscountAmount)",
                toParamMap(voucher));
        throwsExceptionIfNotSuccess(update);

        return voucher;
    }

    public Voucher update(final Voucher voucher) {
        final int update = jdbcTemplate.update(
                "UPDATE vouchers SET discount_amount = :discountAmount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));
        throwsExceptionIfNotSuccess(update);

        return voucher;
    }

    public Voucher updateEmail(final Voucher voucher, final String email) {
        final int update = jdbcTemplate.update(
                "UPDATE vouchers SET customer_email = \"" + email + "\" WHERE voucher_id = :voucherId",
                toParamMap(voucher));
        throwsExceptionIfNotSuccess(update);

        return voucher;
    }

    @Override
    public List<Customer> findCustomer(final UUID voucherId) {
        return jdbcTemplate.query(
                "SELECT c.customer_id, c.customer_name, c.customer_email, c.last_login_at, c.created_at FROM customers c "
                        + "JOIN vouchers v ON c.customer_email = v.customer_email "
                        + "WHERE v.voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerRowMapper);
    }

    @Override
    public Optional<List<Voucher>> findByEmail(final String email) {
        return Optional.of(
                jdbcTemplate.query("select * from vouchers WHERE customer_email = \"" + email + "\"", voucherRowMapper));
    }

    @Override
    public void delete(final UUID voucherId) {
        final int update = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        throwsExceptionIfNotSuccess(update);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    private void throwsExceptionIfNotSuccess(final int update) {
        if (update != SUCCESS) {
            throw new NoSuchElementException("입력된 바우처가 존재하지 않습니다.");
        }
    }
}