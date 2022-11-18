package org.programmers.weekly.mission.domain.wallet;

import org.programmers.weekly.mission.domain.customer.model.Customer;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.model.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class WalletRepository {
    private static final Logger logger = LoggerFactory.getLogger(WalletRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long discount = resultSet.getInt("discount");

        return new VoucherDto(voucherId, type, discount);
    };
    private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String customerEmail = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return new Customer(customerId, customerName, customerEmail, lastLoginAt, createdAt);
    };

    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public WalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Wallet insert(Wallet wallet) {
        int update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                toParamMap(wallet));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }

        return wallet;
    }

    public List<Voucher> findVoucherByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM vouchers INNER JOIN wallet ON vouchers.voucher_id = wallet.voucher_id WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);
    }

    public List<Customer> findCustomerByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("SELECT * FROM customers INNER JOIN wallet ON customers.customer_id = wallet.customer_id WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerRowMapper);
    }

    public void deleteVouchersByCustomerId(UUID customerId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id in (SELECT voucher_id FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId))",
                Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        return new HashMap<>() {{
            put("walletId", wallet.getWalletId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
        }};
    }
}
