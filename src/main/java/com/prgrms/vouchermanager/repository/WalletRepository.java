package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.domain.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
public class WalletRepository {
    private final JdbcTemplate template;

    public WalletRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Wallet create(Wallet wallet) {
        template.update("insert into wallets(wallet_id, voucher_id, customer_id) values(UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                wallet.getWalletId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes());
        return wallet;
    }

    public List<Wallet> findByCustomerId(UUID id) {
        return template.query("select * from wallets where customer_id = UUID_TO_BIN(?)", walletRowMapper());
//        walletList.forEach(wallet -> {
//            UUID voucherId = wallet.getVoucherId();
//            template.queryForObject("select * from vouchers where voucher id = UUID_TO_BIN(?)", )
//        });
    }

    public List<Wallet> findByVoucherId(UUID id) {
        return template.query("select * from wallets where voucher_id = UUID_TO_BIN(?)", walletRowMapper());
    }

    public void delete(UUID customerId, UUID voucherId) {
        template.update("delete from wallets where customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)",
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            return new Wallet(convertBytesToUUID(rs.getBytes("wallet_id")),
                    convertBytesToUUID(rs.getBytes("voucher_id")),
                    convertBytesToUUID(rs.getBytes("customer_id")));
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
