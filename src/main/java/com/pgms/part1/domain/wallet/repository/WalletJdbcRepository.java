package com.pgms.part1.domain.wallet.repository;

import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WalletJdbcRepository implements WalletRepository{

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Wallet mapWallet(ResultSet resultSet) throws SQLException {
        return new Wallet(resultSet.getLong("id"), resultSet.getLong("voucher_id"), resultSet.getLong("customer_id"));
    }

    @Override
    public List<Wallet> findWalletByCustomerId(Long id) {
        String listWalletsByCustomerSql = "SELECT * FROM WALLETS WHERE cutomer_id = ?";
        return jdbcTemplate.query(listWalletsByCustomerSql, (resultSet, i) -> mapWallet(resultSet), id);
    }

    @Override
    public List<Wallet> findWalletByVoucherId(Long id) {
        String listWalletsByVoucherSql = "SELECT * FROM WALLETS WHERE voucher_id = ?";
        return jdbcTemplate.query(listWalletsByVoucherSql, new Object[]{id}, (resultSet, i) -> mapWallet(resultSet));
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
