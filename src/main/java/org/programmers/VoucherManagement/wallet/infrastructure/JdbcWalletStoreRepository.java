package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.exception.WalletException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.programmers.VoucherManagement.global.response.ErrorCode.FAIL_TO_DELETE_WALLET;
import static org.programmers.VoucherManagement.global.response.ErrorCode.FAIL_TO_INSERT_WALLET;

@Repository
@Primary
public class JdbcWalletStoreRepository implements WalletStoreRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcWalletStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Wallet insert(Wallet wallet) {
        String sql = "INSERT INTO wallet_table(wallet_id, voucher_id, member_id, created_at) VALUES (?,?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                wallet.getWalletId(),
                wallet.getVoucher().getVoucherId(),
                wallet.getMember().getMemberId(),
                wallet.getCreatedAt()
        );
        if (insertCount != 1) {
            throw new WalletException(FAIL_TO_INSERT_WALLET);
        }
        return wallet;
    }

    @Override
    public void delete(String walletId) {
        String sql = "DELETE FROM wallet_table WHERE wallet_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                walletId);
        if (deleteCount != 1) {
            throw new WalletException(FAIL_TO_DELETE_WALLET);
        }
    }
}
