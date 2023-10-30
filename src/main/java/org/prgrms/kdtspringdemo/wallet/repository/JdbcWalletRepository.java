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
    private final Gson gson = new Gson();
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
                wallet.getWalletId().toString().getBytes(),
                wallet.getCustomerId().toString().getBytes());
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
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query("select * from vouchers v inner join wallet_customer_voucher inter" +
                        "on v.voucher_id = inter.voucher_id WHERE inter.customer_id = UUID_TO_BIN(?)",
                voucherRowMapper
        );
    }

    @Override
    public void deleteVoucherByVoucherId(UUID customerId, UUID voucherId) {
        var update = jdbcTemplate.update("DELETE from wallet_customer_voucher WHERE customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)",
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
        if(update != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers c inner join wallet_customer_voucher inter" +
                        "on c.customer_id = inter.customer_id where inter.voucher_id = UUID_TO_BIN(?)",
                customerRowMapper,
                voucherId.toString().getBytes()));
    }

    @Override
    public void addVoucherByCustomerId(UUID walletId, UUID customerId, UUID voucherId) {
        var update = jdbcTemplate.update("INSERT INTO wallet_customer_voucher(wallet_id, customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))",
                walletId.toString().getBytes(),
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());
        if(update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallet");
    }
}
