package org.programmers.weekly.mission.domain.wallet;

import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.programmers.weekly.mission.domain.voucher.model.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class WalletRepository {
    private static final Logger logger = LoggerFactory.getLogger(WalletRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID walletId = toUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(walletId, customerId, voucherId);
    };
    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String type = resultSet.getString("type");
        long discount = resultSet.getInt("discount");

        return new VoucherDto(voucherId, type, discount);
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

    private Map<String, Object> toParamMap(Wallet wallet) {
        return new HashMap<>() {{
            put("walletId", wallet.getWalletId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
        }};
    }
}
