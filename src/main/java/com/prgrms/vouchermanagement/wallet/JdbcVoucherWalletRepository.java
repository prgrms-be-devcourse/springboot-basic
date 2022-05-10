package com.prgrms.vouchermanagement.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcVoucherWalletRepository  implements VoucherWalletRepository {

    public static final String INSERT_WALLET_SQL = "INSERT INTO voucher_wallet(voucher_id, customer_id, created_at) VALUES (:voucherId, :customerId, :createdAt)";
    public static final String DELETE_SQL = "DELETE FROM voucher_wallet WHERE wallet_id=:walletId";
    public static final String SELECT_WALLET_BY_ID = "SELECT * FROM voucher_wallet WHERE wallet_id=:walletId";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public JdbcVoucherWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Wallet wallet) throws DataAccessException {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("voucherId", wallet.getVoucherId())
                .addValue("customerId", wallet.getCustomerId())
                .addValue("createdAt", wallet.getCreatedAt());
        jdbcTemplate.update(INSERT_WALLET_SQL, parameterSource, keyHolder, new String[]{"wallet_id"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public void removeWallet(Long walletId) throws DataAccessException {
        try {
            jdbcTemplate.update(DELETE_SQL, Collections.singletonMap("walletId", walletId));
        } catch (DataAccessException e) {
            log.error("fail to execute query", e);
            throw e;
        }
    }

    @Override
    public Optional<Wallet> findWallet(Long walletId) throws DataAccessException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_WALLET_BY_ID,
                    Collections.singletonMap("walletId", walletId), walletRowMapper));
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

    private final RowMapper<Wallet> walletRowMapper = (rs, rowNum) -> {
        Long walletId = rs.getLong("wallet_id");
        Long voucherId = rs.getLong("voucher_id");
        Long customerId = rs.getLong("customer_id");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        return Wallet.of(walletId, customerId, voucherId, createdAt);
    };
}
