package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.global.exception.NotUpdateException;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JdbcWalletCommandRepository implements WalletCommandRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletCommandRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        String sql = "INSERT INTO wallet(id, member_id, voucher_id) VALUES (?, ?, ?)";
        int update = jdbcTemplate.update(sql, wallet.getWalletId().toString(),
                wallet.getMemberId().toString(),
                wallet.getVoucherId().toString());
        if (update != 1) {
            throw new NotUpdateException("db에 insert가 수행되지 못했습니다.");
        }
        return wallet;
    }

    @Override
    public void deleteById(UUID walletId) {
        String sql = "DELETE FROM wallet WHERE id = ?";
        int update = jdbcTemplate.update(sql, walletId.toString());
        if (update != 1) throw new NotUpdateException("db에 delete가 수행되지 못했습니다.");
    }
}
