package org.prgms.order.voucher.wallet;

import org.prgms.order.voucher.entity.VoucherCreateStretage;
import org.prgms.order.voucher.entity.VoucherData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;


@Primary
@Repository
@Profile({"default"})
public class NamedJdbcWalletRepository implements WalletRepository{
    private final Logger logger = LoggerFactory.getLogger(NamedJdbcWalletRepository.class); //this.getClass()

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NamedJdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        return Wallet.builder().
                walletId(walletId).
                customerId(customerId).
                voucherId(voucherId).build();
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Wallet wallet){
        return new HashMap<>() {{
            put("wallet_id", wallet.getWalletId() != null?wallet.getWalletId().toString().getBytes() : null);
            put("customer_id", wallet.getCustomerId() != null?wallet.getCustomerId().toString().getBytes() : null);
            put("voucher_id", wallet.getVoucherId() != null?wallet.getVoucherId().toString().getBytes() : null);
        }};
    }



    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update("insert into wallets(wallet_id, customer_id, voucher_id) values " +
                        "(UNHEX(REPLACE(:wallet_id,'-','')), (UNHEX(REPLACE(:customer_id,'-','')), (UNHEX(REPLACE(:voucher_id,'-','')),)",
                toParamMap(wallet));
        if (update != 1) {
            throw new RuntimeException("Nothing was insert");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallets where wallet_id = UNHEX(REPLACE(:wallet_id,'-',''))",
                    Collections.singletonMap("wallet_id",walletId.toString().getBytes()),
                    walletRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query("select * from wallets where customer_id = UNHEX(REPLACE(:customer_id,'-',''))",
                    Collections.singletonMap("customer_id",customerId.toString().getBytes()),
                    walletRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        try {
            return jdbcTemplate.query("select * from wallets where voucher_id = UNHEX(REPLACE(:voucher_id,'-',''))",
                    Collections.singletonMap("voucher_id",voucherId.toString().getBytes()),
                    walletRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public List<Wallet> findByCustomerAvailable(UUID customerId) {
        try {
            return jdbcTemplate.query("select * from wallets where customer_id = UNHEX(REPLACE(:customer_id,'-',''))",
                    Collections.singletonMap("customer_id",customerId.toString().getBytes()),
                    walletRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return null;
        }
    }

    @Override
    public Wallet useVoucher(Wallet wallet) {
        var update = jdbcTemplate.update("update wallets set . used_at = :used_at wallet_id = UNHEX(REPLACE(:wallet_id,'-',''))",
                toParamMap(wallet));
        if (update != 1) {
            throw new RuntimeException("Nothing was insert");
        }
        return wallet;
    }

    @Override
    public void deleteByWalletId(UUID walletId) {
        jdbcTemplate.update("delete from wallets where wallet_id = UNHEX(REPLACE(:wallet_id,'-',''))",
                Collections.singletonMap("wallet_id",walletId.toString().getBytes()));
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update("delete from wallets where customer_id = UNHEX(REPLACE(:customer_id,'-',''))",
                Collections.singletonMap("customer_id",customerId.toString().getBytes()));
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        jdbcTemplate.update("delete from wallets where voucher_id = UNHEX(REPLACE(:voucher_id,'-',''))",
                Collections.singletonMap("voucher_id",voucherId.toString().getBytes()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from wallets",Collections.emptyMap());
    }

}
