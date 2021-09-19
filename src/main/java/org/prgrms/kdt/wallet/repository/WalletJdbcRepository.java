package org.prgrms.kdt.wallet.repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.prgrms.kdt.wallet.model.Wallet;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class WalletJdbcRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private String INSERT = """
        INSERT INTO wallet (wallet_id, customer_id, voucher_id, created_at, is_used)
        VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :createdAt, :isUsed)""";
    private String DELETE_BY_CUSTOMER_VOUCHER = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)";


    public WalletJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update(INSERT, toParamMap(wallet));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }

    @Override
    public void deleteByCustomerVoucher(UUID customerId, UUID voucherId) {
        var update = jdbcTemplate.update(
            DELETE_BY_CUSTOMER_VOUCHER,
            Map.of(
                "customerId", customerId.toString().getBytes(),
                "voucherId", voucherId.toString().getBytes()));
        if (update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        var paramMap = new HashMap<String, Object>();

        paramMap.put("walletId", wallet.getWalletId().toString().getBytes());
        paramMap.put("customerId", wallet.getCustomerId().toString().getBytes());
        paramMap.put("voucherId", wallet.getVoucherId().toString().getBytes());
        paramMap.put("createdAt", Timestamp.valueOf(wallet.getCreatedAt()));
        paramMap.put("isUsed", wallet.isUsed());

        return paramMap;
    }
}
