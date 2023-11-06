package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_VOUCHER_ROW_MAPPER;
import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Repository
@Profile("dev")
public class VoucherJdbcRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        Integer amount = resultSet.getInt("amount");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        byte[] customerIdBytes = resultSet.getBytes("customer_id");
        UUID customerId = null;
        if (customerIdBytes != null) {
            customerId = toUUID(customerIdBytes);
        }

        if (type.equals(FIXED.toString())) {
            return new FixedAmountVoucher(voucherId, amount, createdAt, customerId);
        }
        if (type.equals(PERCENT.toString())) {
            return new PercentDiscountVoucher(voucherId, amount, createdAt, customerId);
        }

        logger.error("JdbcVoucherRepository RowMapper Error");
        throw new RuntimeException(EXCEPTION_VOUCHER_ROW_MAPPER.getMessage());
    };
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public VoucherJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, amount, created_at, customer_id) "
                        + "VALUES (UUID_TO_BIN(?), ?, ?, ?, UUID_TO_BIN(?))",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getType(),
                voucher.getAmount(),
                voucher.getCreatedAt(),
                voucher.getCustomerId() != null ? voucher.getCustomerId().toString().getBytes() : null
        );

        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        int deletedRows = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());

        if (deletedRows == 0) {
            logger.warn("No voucher with ID: {} was found to delete.", voucherId);
        } else {
            logger.info("Voucher with ID: {} deleted successfully.", voucherId);
        }
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
