package com.prgrms.vouchermanager.repository.wallet;

import com.prgrms.vouchermanager.domain.wallet.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.message.QueryMessage.*;

@Repository
public class WalletRepository {
    private final JdbcTemplate template;

    public WalletRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Wallet create(Wallet wallet) {
        template.update(INSERT_WALLET.getMessage(),
                wallet.getWalletId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes());
        return wallet;
    }

    public List<Wallet> findByCustomerId(UUID id) {
        return template.query(FIND_BY_CUSTOMER_ID_WALLET.getMessage(), walletRowMapper(), id.toString().getBytes());
    }

    public List<Wallet> findByVoucherId(UUID id) {
        return template.query(FIND_BY_VOUCHER_ID_WALLET.getMessage(), walletRowMapper(), id.toString().getBytes());
    }

    public int delete(UUID customerId, UUID voucherId) {
        return template.update(DELETE_WALLET.getMessage(),
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> new Wallet(convertBytesToUUID(rs.getBytes("wallet_id")),
                    convertBytesToUUID(rs.getBytes("voucher_id")),
                    convertBytesToUUID(rs.getBytes("customer_id")));
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
