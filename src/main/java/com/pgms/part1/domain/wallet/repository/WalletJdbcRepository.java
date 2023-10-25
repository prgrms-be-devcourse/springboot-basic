package com.pgms.part1.domain.wallet.repository;

import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletJdbcRepository implements WalletRepository{

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wallet> findWalletByCustomerId(Long id) {
        return null;
    }

    @Override
    public List<Wallet> findWalletByVoucherId(Long id) {
        return null;
    }

    @Override
    public void addWallet(Wallet wallet) {
        String addWalletSql = "INSERT INTO WALLETS(id, voucher_id, customer_id) values (?, ?, ?)";
        jdbcTemplate.update(addWalletSql, wallet.id(), wallet.voucherId(), wallet.customerId());
    }

    @Override
    public void deleteWallet(Long id) {
        String deleteWalletSql = "DELETE FROM WALLETS WHERE id = ?";
        jdbcTemplate.update(deleteWalletSql, id);
    }
}
