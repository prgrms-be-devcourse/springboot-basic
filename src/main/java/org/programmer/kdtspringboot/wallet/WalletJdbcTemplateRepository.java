package org.programmer.kdtspringboot.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletJdbcTemplateRepository implements WalletRepository {

    private static final Logger logger = LoggerFactory.getLogger(WalletJdbcTemplateRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Wallet> walletRowMapper = (resultSet, rowNum) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(walletId, customerId, voucherId);
    };

    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update(
                "insert into wallet(wallet_id, customer_id, voucher_id ) values (UNHEX(REPLACE(?, '-', '')), UNHEX(REPLACE(?, '-', '')), UNHEX(REPLACE(?, '-', '')))",
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes(),
                wallet.getVoucherId().toString().getBytes()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findByWalletId(UUID walletId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from wallet where wallet_id = UNHEX(REPLACE(?, '-', ''))",
                    walletRowMapper,
                    walletId.toString().getBytes()));
        }catch (EmptyResultDataAccessException e){
            logger.error("Got empty result",e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
                "select * from wallet where customer_id = UNHEX(REPLACE(?, '-', ''))",
                walletRowMapper,
                customerId.toString().getBytes()
        );
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(
                "select * from wallet where voucher_id = UNHEX(REPLACE(?, '-', ''))",
                walletRowMapper,
                voucherId.toString().getBytes()
        );
    }

    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query(
                "select * from wallet",
                walletRowMapper
        );
    }

    @Override
    public void deleteVoucher(UUID voucherId) {
        jdbcTemplate.update("delete from wallet where voucher_id = UNHEX(REPLACE(?, '-', ''))",
                voucherId.toString().getBytes()
        );
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
