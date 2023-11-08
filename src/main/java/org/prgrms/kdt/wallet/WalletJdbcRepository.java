package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.voucher.VoucherMessage.EXCEPTION_VOUCHER_ROW_MAPPER;
import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;
import static org.prgrms.kdt.voucher.domain.VoucherType.PERCENT;

@Repository
public class WalletJdbcRepository implements WalletRepository{
    private static final Logger logger = LoggerFactory.getLogger(WalletJdbcRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(customerId.toString(), voucherId.toString());
    };

    public WalletJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet save(Wallet wallet) {
        int update = jdbcTemplate.update("INSERT INTO wallets(wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes());
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(String walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallets WHERE wallet_id = UUID_TO_BIN(?)",
                    walletRowMapper,
                    walletId.getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findByCustomerId(String customerId) {
        return jdbcTemplate.query("select * from wallets WHERE customer_id = UUID_TO_BIN(?)",
                walletRowMapper,
                customerId.getBytes());
    }

    @Override
    public  Optional<Wallet> findByVoucherId(String voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallets WHERE voucher_id = UUID_TO_BIN(?)",
                    walletRowMapper,
                    voucherId.getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteByCustomerId(String customerId) {
        jdbcTemplate.update("DELETE FROM wallets WHERE customer_id = UUID_TO_BIN(?)", customerId.getBytes());
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
