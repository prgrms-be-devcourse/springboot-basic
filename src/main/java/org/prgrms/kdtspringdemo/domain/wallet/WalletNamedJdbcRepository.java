package org.prgrms.kdtspringdemo.domain.wallet;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.VoucherManagerUtil.toUUID;

@Repository
public class WalletNamedJdbcRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<UUID> walletRowMapper = (rs, rowNum) -> toUUID(rs.getBytes(1));

    public WalletNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<UUID> findVoucherIdsByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select voucher_id from wallet where customer_id = UUID_TO_BIN(:customerId);", Collections.singletonMap("customerId", customerId.toString()), walletRowMapper);
    }

    @Override
    public List<UUID> findCustomerIdsByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("select customer_id from wallet where voucher_id = UUID_TO_BIN(:voucherId);", Collections.singletonMap("voucherId", voucherId.toString()), walletRowMapper);
    }

    @Override
    public Wallet addWallet(Wallet wallet) {
        jdbcTemplate.update("INSERT INTO wallet(voucher_id, customer_id) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId));", toParamMap(wallet));
        return wallet;
    }

    @Override
    public void deleteWallet(Wallet wallet) {
        jdbcTemplate.update("delete from wallet where customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId);", toParamMap(wallet));
    }

    @Override
    public void deleteAllWallet() {
        jdbcTemplate.getJdbcTemplate().update("delete from wallet");
    }

    @Override
    public int count() {
        return jdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from wallet", Integer.class);
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        return new HashMap<>() {{
            put("customerId", wallet.getCustomerId().toString());
            put("voucherId", wallet.getVoucherId().toString());
        }};
    }
}
