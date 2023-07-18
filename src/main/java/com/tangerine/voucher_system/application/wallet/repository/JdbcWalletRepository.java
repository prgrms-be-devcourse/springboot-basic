package com.tangerine.voucher_system.application.wallet.repository;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.wallet.model.Wallet;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile({"default", "test"})
public class JdbcWalletRepository implements WalletRepository {

    static RowMapper<Wallet> walletRowMapper = (resultSet, rowNumber) -> {
        UUID walletId = UUID.fromString(resultSet.getString("wallet_id"));
        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        return new Wallet(walletId, voucherId, customerId);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("walletId", wallet.walletId().toString());
        paramMap.put("voucherId", wallet.voucherId().toString());
        paramMap.put("customerId", wallet.customerId().toString());
        return paramMap;
    }

    @Override
    public void insert(Wallet wallet) {
        try {
            int updateResult = jdbcTemplate.update(
                    "INSERT INTO wallets(wallet_id, voucher_id, customer_id) VALUES (:walletId, :voucherId, :customerId)",
                    toParamMap(wallet)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e);
        }
    }

    @Override
    public void update(Wallet wallet) {
        try {
            int updateResult = jdbcTemplate.update(
                    "UPDATE wallets SET voucher_id = :voucherId, customer_id = :customerId WHERE wallet_id = :walletId",
                    toParamMap(wallet)
            );
            if (updateResult != 1) {
                throw new InvalidDataException(ErrorMessage.INVALID_CREATION.getMessageText());
            }
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e);
        }
    }

    @Override
    public void deleteById(UUID walletId) {
        try {
            jdbcTemplate.update(
                    "DELETE FROM wallets WHERE wallet_id = :walletId",
                    Collections.singletonMap("walletId", walletId.toString())
            );
        } catch (DataAccessException e) {
            throw new InvalidDataException(ErrorMessage.INVALID_SQL.getMessageText(), e);
        }
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query(
                    "SELECT wallet_id, voucher_id, customer_id FROM wallets WHERE customer_id = :customerId",
                    Collections.singletonMap("customerId", customerId.toString()),
                    walletRowMapper
            );
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        try {
            return jdbcTemplate.query(
                    "SELECT wallet_id, voucher_id, customer_id FROM wallets WHERE voucher_id = :voucherId",
                    Collections.singletonMap("voucherId", voucherId.toString()),
                    walletRowMapper
            );
        } catch (DataAccessException e) {
            return List.of();
        }
    }

}
