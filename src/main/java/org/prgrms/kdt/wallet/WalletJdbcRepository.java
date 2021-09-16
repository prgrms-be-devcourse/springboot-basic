package org.prgrms.kdt.wallet;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 1:51 오전
 */
@Repository
public class WalletJdbcRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Wallet wallet) {
        jdbcTemplate.update("INSERT INTO wallets (wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                UUID.randomUUID().toString().getBytes(),
                wallet.customerId().toString().getBytes(),
                wallet.voucherId().toString().getBytes());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallets");
    }

    public void deleteByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        jdbcTemplate.update("DELETE FROM wallets "
                            + "WHERE customer_id = UUID_TO_BIN(?) AND voucher_id = UUID_TO_BIN(?) "
                            + "ORDER BY created_at ASC "
                            + "LIMIT 1",
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
    }
}
