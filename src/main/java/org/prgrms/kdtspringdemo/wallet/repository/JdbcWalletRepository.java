package org.prgrms.kdtspringdemo.wallet.repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("DB")
public class JdbcWalletRepository implements WalletRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final Gson gson = new Gson();
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var stringVouchers = resultSet.getString("vouchers");
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringVouchers);

        var vouchers = toVoucherList(jsonArray);
        return new Wallet(walletId, customerId, vouchers);
    };

    static List<UUID> toVoucherList(JsonArray jsonArray) {
        List<UUID> vouchers = new ArrayList<>();
        jsonArray.forEach(data -> {
            String stringUUID = data.toString();
            if(stringUUID.length()>4) vouchers.add(UUID.fromString(stringUUID.substring(4, stringUUID.length()-4)));
        });
        return vouchers;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Autowired
    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Wallet insert(Wallet wallet) {
        var update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, vouchers) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), ?)",
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes(),
                gson.toJson(wallet.getVouchers()));
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallet WHERE wallet_id = UUID_TO_BIN(?)",
                    walletRowMapper,
                    walletId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<UUID>> findVouchersByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from wallet WHERE customer_id = UUID_TO_BIN(?)",
                    walletRowMapper,
                    customerId.toString().getBytes()).getVouchers());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<UUID> deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        Wallet wallet = jdbcTemplate.queryForObject("select * from wallet WHERE customer_id = UUID_TO_BIN(?)",
                walletRowMapper,
                customerId.toString().getBytes());

        List<UUID> vouchers = wallet.getVouchers();
        vouchers.remove(voucherId);
        vouchers.stream().forEach(uuid -> logger.info(uuid.toString()));

        var update = jdbcTemplate.update("UPDATE wallet SET vouchers = (?) WHERE wallet_id = UUID_TO_BIN(?)",
                gson.toJson(vouchers),
                wallet.getWalletId().toString().getBytes());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return vouchers;
    }

    @Override
    public List<UUID> findCustomerByVoucherId(UUID voucherId) {
        List<Wallet> walletList = jdbcTemplate.query("select * from wallet", walletRowMapper);
        List<UUID> customers = new ArrayList<>();
        walletList.stream()
                .filter(wallet -> wallet.getVouchers().contains(voucherId))
                .forEach(wallet -> customers.add(wallet.getCustomerId()));
        return customers;
    }

    @Override
    public List<UUID> addVoucherByCustomerId(UUID customerId, UUID voucherId) {
        Wallet wallet = jdbcTemplate.queryForObject("select * from wallet where customer_id = UUID_TO_BIN(?)"
                ,walletRowMapper,
                customerId.toString().getBytes());
        List<UUID> newVouchers = wallet.getVouchers();
        newVouchers.add(voucherId);

        jdbcTemplate.update("UPDATE wallet SET vouchers = (?) WHERE wallet_id = UUID_TO_BIN(?)",
                gson.toJson(newVouchers),
                wallet.getWalletId().toString().getBytes());
        return newVouchers;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallet");
    }
}
