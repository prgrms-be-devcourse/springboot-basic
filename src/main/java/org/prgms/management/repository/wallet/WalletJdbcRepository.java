package org.prgms.management.repository.wallet;

import org.prgms.management.model.voucher.VoucherCreator;
import org.prgms.management.model.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static org.prgms.management.util.JdbcUtils.toUUID;

@Repository
@Profile({"local-db", "dev", "test"})
public class WalletJdbcRepository implements WalletRepository {
    private static final Logger logger = LoggerFactory.getLogger(WalletJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        var executeUpdate = jdbcTemplate.update("INSERT INTO wallets(" +
                        "wallet_id, customer_id, voucher_id, created_at) " +
                        "VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), " +
                        " UUID_TO_BIN(:voucherId), :createdAt)",
                toParamMap(wallet));

        if (executeUpdate != 1) {
            return null;
        }

        return wallet;
    }

    @Override
    public List<Wallet> findAll() {
        try {
            return jdbcTemplate.query(""" 
                            SELECT w.wallet_id,
                                   w.customer_id,
                                   w.voucher_id as "voucher_id",
                                   v.name as "voucher_name",
                                   v.type as "voucher_type",
                                   v.discount_num as "voucher_discount_num",
                                   v.created_at as "voucher_created_at",
                                   w.created_at
                            FROM wallets w
                                JOIN vouchers v
                                ON w.voucher_id = v.voucher_id
                            """,
                    rowMapper);
        } catch (
                EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Wallet> findById(UUID walletId) {
        try {
            return jdbcTemplate.query("""
                            SELECT w.wallet_id,
                                   w.customer_id,
                                   w.voucher_id as "voucher_id",
                                   v.name as "voucher_name",
                                   v.type as "voucher_type",
                                   v.discount_num as "voucher_discount_num",
                                   v.created_at as "voucher_created_at",
                                   w.created_at
                            FROM wallets w
                                JOIN vouchers v
                                ON w.voucher_id = v.voucher_id
                            WHERE wallet_id = UUID_TO_BIN(:walletId)
                            """,
                    Collections.singletonMap("walletId", walletId.toString().getBytes()),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query("""
                            SELECT w.wallet_id,
                                   w.customer_id,
                                   w.voucher_id as "voucher_id",
                                   v.name as "voucher_name",
                                   v.type as "voucher_type",
                                   v.discount_num as "voucher_discount_num",
                                   v.created_at as "voucher_created_at",
                                   w.created_at
                            FROM wallets w
                                JOIN vouchers v
                                ON w.voucher_id = v.voucher_id
                            WHERE customer_id = UUID_TO_BIN(:customerId)
                            """,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public Wallet delete(Wallet wallet) {
        var executeUpdate = jdbcTemplate.update(
                "DELETE FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
                toParamMap(wallet));

        if (executeUpdate != 1) {
            return null;
        }

        return wallet;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallets", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        Map<String, Object> map = new HashMap<>();
        map.put("walletId", wallet.getWalletId().toString().getBytes());
        map.put("customerId", wallet.getCustomerId().toString().getBytes());
        map.put("voucherId", wallet.getVoucher().getVoucherId().toString().getBytes());
        map.put("createdAt", Timestamp.valueOf(wallet.getCreatedAt()));
        return map;
    }

    private static final RowMapper<Wallet> rowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucher = VoucherCreator.createVoucher(
                toUUID(resultSet.getBytes("voucher_id")),
                resultSet.getInt("voucher_discount_num"),
                resultSet.getString("voucher_name"),
                resultSet.getString("voucher_type"),
                resultSet.getTimestamp("voucher_created_at").toLocalDateTime());
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return Wallet.getWallet(walletId, customerId, voucher, createdAt);
    };
}
