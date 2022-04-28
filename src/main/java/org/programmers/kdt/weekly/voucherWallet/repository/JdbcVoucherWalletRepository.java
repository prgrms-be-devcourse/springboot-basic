package org.programmers.kdt.weekly.voucherWallet.repository;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//import org.programmers.kdt.weekly.utils.UtilFunction;
//import org.programmers.kdt.weekly.voucherWallet.model.VoucherWallet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.dao.InvalidDataAccessApiUsageException;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class JdbcVoucherWalletRepository implements VoucherWalletRepository {
//
//    private static final Logger logger = LoggerFactory.getLogger(
//        JdbcVoucherWalletRepository.class);
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    private final RowMapper<VoucherWallet> voucherWalletRowMapper = (rs, rowMapper) -> {
//        var walletId = UtilFunction.toUUID(rs.getBytes("wallet_id"));
//        var customerId = UtilFunction.toUUID(rs.getBytes("customer_id"));
//        var voucherId = UtilFunction.toUUID(rs.getBytes("voucher_id"));
//        var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
//        var expirationAt = rs.getTimestamp("expiration_at").toLocalDateTime();
//
//        return new VoucherWallet(walletId, customerId, voucherId, createdAt);
//    };
//
//    private Map<String, Object> toParamMap(VoucherWallet voucherWallet) {
//        HashMap<String, Object> voucherWalletMap = new HashMap<>();
//        var voucherWalletData = voucherWallet.serializeVoucherWallet().split(",");
//        voucherWalletMap.put("walletId", voucherWalletData[0]);
//        voucherWalletMap.put("customerId", voucherWalletData[1]);
//        voucherWalletMap.put("voucherId", voucherWalletData[2]);
//        voucherWalletMap.put("createdAt", voucherWalletData[3]);
//        voucherWalletMap.put("expirationAt", voucherWalletData[4]);
//
//        return voucherWalletMap;
//    }
//
//    @Override
//    public VoucherWallet insert(VoucherWallet voucherWallet) {
//        String insertSql =
//            "INSERT INTO voucher_wallet(wallet_id, customer_id, voucher_id, created_at, expiration_at) "
//                + "VALUES (UNHEX(REPLACE(:walletId, '-', '')), UNHEX(REPLACE(:customerId, '-', '')), UNHEX(REPLACE(:voucherId, '-', '')),:createdAt, :expirationAt)";
//        try {
//            var update = namedParameterJdbcTemplate.update(insertSql, toParamMap(voucherWallet));
//        } catch (InvalidDataAccessApiUsageException e) {
//            logger.error("voucher wallet insertSql error -> {}", e);
//        }
//
//        return voucherWallet;
//    }
//
//    @Override
//    public List<VoucherWallet> findAll() {
//        String selectSql = "SELECT * FROM voucher_wallet";
//        return namedParameterJdbcTemplate.query(selectSql,
//            Collections.emptyMap(),
//            voucherWalletRowMapper);
//    }
//
//    @Override
//    public Optional<VoucherWallet> findByWalletId(UUID walletId) {
//        String selectByWalletIdSql =
//            "SELECT * FROM voucher_wallet WHERE customer_id = wallet_id = UNHEX(REPLACE(:walletId, '-', ''))";
//        try {
//            return Optional.of(namedParameterJdbcTemplate.queryForObject(selectByWalletIdSql,
//                Collections.singletonMap("walletId",
//                    walletId.toString().getBytes()), voucherWalletRowMapper));
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("voucher Wallet findByWalletId empty Result ", e);
//
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public List<VoucherWallet> findByCustomerId(UUID customerId) {
//        String selectByCustomerIdSql =
//            "SELECT * FROM voucher_wallet WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
//        try {
//            return namedParameterJdbcTemplate.query(selectByCustomerIdSql,
//                Collections.singletonMap("customerId", customerId.toString().getBytes()),
//                voucherWalletRowMapper);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("voucher Wallet findByCustomerId empty Result ", e);
//
//            return Collections.emptyList();
//        }
//    }
//
//    @Override
//    public Optional<UUID> deleteById(UUID customerId, UUID walletId) {
//        String DELETE_BY_ID_SQL =
//            "DELETE FROM voucher_wallet WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))" +
//                "AND wallet_id = UNHEX(REPLACE(:walletId, '-', ''))";
//        try {
//            this.namedParameterJdbcTemplate.update(DELETE_BY_ID_SQL,
//                Map.of("customerId", customerId.toString().getBytes(), "walletId",
//                    walletId.toString().getBytes()));
//
//            return Optional.ofNullable(walletId);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("voucher wallet deleteById empty result ", e);
//
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public void deleteAllByCustomerId(UUID customerId) {
//        String deleteSql = "DELETE FROM voucher_wallet WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
//        try {
//            this.namedParameterJdbcTemplate.update(deleteSql,
//                Collections.singletonMap("customerId", customerId.toString().getBytes()));
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("voucher wallet deleteAll empty result ", e);
//        }
//    }
//}