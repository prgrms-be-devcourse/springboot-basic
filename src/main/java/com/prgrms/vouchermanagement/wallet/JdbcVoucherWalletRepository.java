package com.prgrms.vouchermanagement.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcVoucherWalletRepository  implements VoucherWalletRepository {

    public static final String INSERT_WALLET_SQL = "INSERT INTO voucher_wallet(wallet_id, voucher_id, customer_id, created_at) VALUES (:walletId, :voucherId, :customerId, :createdAt)";
    public static final String DELETE_SQL = "DELETE FROM voucher_wallet WHERE wallet_id=:walletId";
    public static final String SELECT_WALLET_BY_ID = "SELECT * FROM voucher_wallet WHERE wallet_id=:walletId";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wallet wallet) throws DataAccessException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("walletId", wallet.getWalletId().toString());
        paramMap.put("voucherId", wallet.getVoucherId().toString());
        paramMap.put("customerId", wallet.getCustomerId().toString());
        paramMap.put("createdAt", wallet.getCreatedAt());
        jdbcTemplate.update(INSERT_WALLET_SQL, paramMap);
    }

    @Override
    public void removeWallet(UUID walletId) throws DataAccessException {
        try {
            jdbcTemplate.update(DELETE_SQL, Collections.singletonMap("walletId", walletId.toString()));
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public Optional<Wallet> findWallet(UUID walletId) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_WALLET_BY_ID,
                    Collections.singletonMap("walletId", walletId.toString()), walletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public List<Wallet> findAll() throws DataAccessException {
        try {
            return jdbcTemplate.query("SELECT * FROM voucher_wallet", Collections.emptyMap(), walletRowMapper);
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    /**
     * 테스트에서 사용
     */
    public void clear() {
        jdbcTemplate.update("DELETE FROM voucher_wallet", Collections.emptyMap());
    }

    private final RowMapper<Wallet> walletRowMapper = (rs, rowNum) -> {
        UUID walletId = UUID.fromString(rs.getString("wallet_id"));
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        UUID customerId = UUID.fromString(rs.getString("customer_id"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return Wallet.of(walletId, customerId, voucherId, createdAt);
    };
}
