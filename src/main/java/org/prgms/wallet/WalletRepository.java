package org.prgms.wallet;

import org.prgms.utils.UuidUtils;
import org.prgms.validator.RepositoryValidator;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Repository
public class WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Wallet wallet) {
        int update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, voucher_id) values (?, ?, ?)", UuidUtils.uuidToBytes(wallet.walletId()), UuidUtils.uuidToBytes(wallet.customerId()), UuidUtils.uuidToBytes(wallet.voucherId()));
        RepositoryValidator.affectedRowMustBeOne(update);
        return update;
    }

    public List<Wallet> findAll() {
        return jdbcTemplate.query("SELECT * FROM wallet", this::mapRowToWallet);
    }

    public int update(Wallet wallet) {
        return jdbcTemplate.update("UPDATE wallet SET customer_id=?, voucher_id=? WHERE wallet_id = ?", wallet.customerId(), wallet.voucherId(), wallet.walletId());
    }

    public List<Wallet> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM wallet WHERE customer_id = ?", this::mapRowToWallet, UuidUtils.uuidToBytes(customerId));
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("SELECT * FROM wallet WHERE voucher_id = ?", this::mapRowToWallet, UuidUtils.uuidToBytes(voucherId));
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallet");
    }

    private Wallet mapRowToWallet(ResultSet rs, int rowNum) {
        try {
            UUID walletId = UuidUtils.bytesToUUID(rs.getBytes("wallet_id"));
            UUID customerId = UuidUtils.bytesToUUID(rs.getBytes("customer_id"));
            UUID voucherId = UuidUtils.bytesToUUID(rs.getBytes("voucher_id"));
            return new Wallet(walletId, customerId, voucherId);
        } catch (SQLException e) {
            throw new DataRetrievalFailureException(MessageFormat.format("데이터를 가져오는 데 실패했습니다. {0}", e.getMessage()));
        }
    }
}
