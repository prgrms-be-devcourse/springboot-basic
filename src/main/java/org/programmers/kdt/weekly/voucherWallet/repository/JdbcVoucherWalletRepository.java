package org.programmers.kdt.weekly.voucherWallet.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("local")
@Repository
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    private static final Logger logger = LoggerFactory.getLogger(
        JdbcVoucherWalletRepository.class);

    private static final String INSERT_SQL = "INSERT INTO voucher_wallet(wallet_id, customer_id, voucher_id, created_at, expiration_at) VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId),:createdAt, :expirationAt)";
    private static final String SELECT_SQL = "SELECT * FROM voucher_wallet WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM voucher_wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND wallet_id = UUID_TO_BIN(:walletId)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM voucher_wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND wallet_id = UUID_TO_BIN(:walletId)";
    private static final String DELETE_SQL = "DELETE FROM voucher_wallet WHERE customer_id = UUID_TO_BIN(:customerId)";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<VoucherWallet> voucherWalletRowMapper = (rs, rowMapper) -> {
        var walletId = UtilFunction.toUUID(rs.getBytes("wallet_id"));
        var customerId = UtilFunction.toUUID(rs.getBytes("customer_id"));
        var voucherId = UtilFunction.toUUID(rs.getBytes("voucher_id"));
        var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        var expirationAt = rs.getTimestamp("expiration_at").toLocalDateTime();

        return new VoucherWallet(walletId, customerId, voucherId, createdAt,
            expirationAt);
    };

    private Map<String, Object> toParamMap(VoucherWallet voucherWallet) {
        HashMap<String, Object> voucherWalletMap = new HashMap<>();
        var voucherWalletData = voucherWallet.serializeVoucherWallet().split(",");
        voucherWalletMap.put("walletId", voucherWalletData[0]);
        voucherWalletMap.put("customerId", voucherWalletData[1]);
        voucherWalletMap.put("voucherId", voucherWalletData[2]);
        voucherWalletMap.put("createdAt", voucherWalletData[3]);
        voucherWalletMap.put("expirationAt", voucherWalletData[4]);

        return voucherWalletMap;
    }

    @Override
    public VoucherWallet insert(VoucherWallet voucherWallet) {
        var update = namedParameterJdbcTemplate.update(INSERT_SQL, toParamMap(voucherWallet));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucherWallet;
    }

    @Override
    public List<VoucherWallet> findAll(UUID customerId) {
        try {
            return namedParameterJdbcTemplate.query(SELECT_SQL,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherWalletRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher Wallet findAll empty result ", e);

            return Collections.emptyList();
        }
    }

    @Override
    public Optional<VoucherWallet> findById(UUID customerId, UUID walletId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                Map.of("customerId", customerId.toString().getBytes(), "walletId",
                    walletId.toString().getBytes()), voucherWalletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher Wallet findById empty Result ", e);

            return Optional.empty();
        }
    }

    @Override
    public Optional<UUID> deleteById(UUID customerId, UUID walletId) {
        try {
            this.namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL,
                Map.of("customerId", customerId.toString().getBytes(), "walletId",
                    walletId.toString().getBytes()));
            return Optional.ofNullable(walletId);
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher wallet deleteById empty result ", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll(UUID customerId) {
        try {
            this.namedParameterJdbcTemplate.update(DELETE_SQL,
                Collections.singletonMap("customerId", customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher wallet deleteAll empty result ", e);
        }
    }
}