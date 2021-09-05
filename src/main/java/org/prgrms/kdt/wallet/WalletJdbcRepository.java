package org.prgrms.kdt.wallet;

import org.springframework.jdbc.core.JdbcTemplate;
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
    public int insert(Wallet wallet) {
        return jdbcTemplate.update("INSERT INTO wallets (wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                wallet.voucherWalletId().toString().getBytes(),
                wallet.customerId().toString().getBytes(),
                wallet.voucherId().toString().getBytes());
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM wallets");
    }
}
