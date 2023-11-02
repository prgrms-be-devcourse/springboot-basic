package org.prgrms.kdtspringdemo.wallet.repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
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
    private final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var walletId = toUUID(resultSet.getBytes("wallet_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));

        return new Wallet(walletId, customerId);
    };

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var amount = Long.parseLong(resultSet.getString("amount"));
        var voucherType = resultSet.getString("voucher_type");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));

        var voucherTypeEnum = VoucherTypeFunction.findByCode(voucherType);
        return voucherTypeEnum.create(voucherId, amount);
    };

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var isBlack = Boolean.parseBoolean(resultSet.getString("is_black"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        return new Customer(customerId, customerName, isBlack);
    };

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
        var update = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?))",
                wallet.getWalletId().toString(),
                wallet.getCustomerId().toString());
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
                    walletId.toString()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAll() {
        return jdbcTemplate.query("select * from wallet", walletRowMapper);
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select voucher.voucher_id, voucher.voucher_type, voucher.amount from voucher inner join wallet_customer_voucher on voucher.voucher_id = wallet_customer_voucher.voucher_id WHERE wallet_customer_voucher.customer_id = UUID_TO_BIN(?)",
                voucherRowMapper,
                customerId.toString()
        );
    }

    @Override
    public void deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        var update = jdbcTemplate.update("DELETE from wallet_customer_voucher WHERE customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)",
                customerId.toString(),
                voucherId.toString());
        if(update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select c.customer_id, c.name, c.is_black from customers c inner join wallet_customer_voucher inter" +
                        "on c.customer_id = inter.customer_id where inter.voucher_id = UUID_TO_BIN(?)",
                customerRowMapper,
                voucherId.toString()));
    }

    @Override
    public void deleteById(UUID walletId) {
        jdbcTemplate.update("DELETE FROM wallet where wallet_id = UUID_TO_BIN(?)",
                walletId.toString());
    }

    @Override
    public void addVoucherByCustomerId(UUID walletId, UUID customerId, UUID voucherId) {
        var update = jdbcTemplate.update("INSERT INTO wallet_customer_voucher(wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                walletId.toString(),
                customerId.toString(),
                voucherId.toString());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallet");
    }
}
