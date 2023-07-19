package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.exception.WalletException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.programmers.VoucherManagement.wallet.exception.WalletExceptionMessage.FAIL_TO_DELETE;
import static org.programmers.VoucherManagement.wallet.exception.WalletExceptionMessage.FAIL_TO_INSERT;

@Repository
@Primary
public class JdbcWalletStoreRepository implements WalletStoreRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcWalletStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Wallet insert(Wallet wallet) {
        String sql = "insert into wallet_table(wallet_id, voucher_id, member_id) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                wallet.getWalletId().toString(),
                wallet.getVoucher().getVoucherId().toString(),
                wallet.getMember().getMemberUUID().toString());
        if (insertCount != 1) {
            throw new WalletException(FAIL_TO_INSERT);
        }
        return wallet;
    }

    @Override
    public void delete(UUID walletId) {
        String sql = "delete from wallet_table where wallet_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                walletId.toString());
        if (deleteCount != 1) {
            throw new WalletException(FAIL_TO_DELETE);
        }
    }
}
