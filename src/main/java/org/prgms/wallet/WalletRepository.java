package org.prgms.wallet;

import org.prgms.utils.UuidUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;

@Repository
public class WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public WalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Wallet wallet) {

        int update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, voucher_id) values (?, ?, ?)",
                UuidUtils.uuidToBytes(wallet.walletId()),
                UuidUtils.uuidToBytes(wallet.customerId()),
                UuidUtils.uuidToBytes(wallet.voucherId())
        );

        checkState(update == 1, "데이터 저장 실패. 유효한 row 갯수가 1이 아님 : %s", update);

        return update;
    }

    public List<Wallet> findAll() {
        return jdbcTemplate.query("SELECT * FROM wallet", this::mapRowToWallet);
    }

    public int update(Wallet wallet) {
        int update = jdbcTemplate.update("UPDATE wallet SET customer_id=?, voucher_id=? WHERE wallet_id = ?", wallet.customerId(), wallet.voucherId(), wallet.walletId());

        checkState(update == 1, "데이터 업데이트 실패, 유효 row 갯수 : %s. 해당하는 데이터가 존재하지 않음", update);

        return update;
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
