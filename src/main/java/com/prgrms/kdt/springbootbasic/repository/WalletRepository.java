package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.Customer;
import com.prgrms.kdt.springbootbasic.entity.Wallet;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class WalletRepository {
    private final Logger logger = LoggerFactory.getLogger(WalletRepository.class);
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WalletRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Wallet wallet){
        return new HashMap<>(){{
            put("wallet_id", wallet.getWalletId().toString().getBytes());
            put("customer_id", wallet.getCustomerId().toString().getBytes());
            put("voucher_id",wallet.getVoucherId().toString().getBytes());
        }};
    }

    private static RowMapper<Wallet> walletRowMapper = (resultSet, i) ->{
        UUID walletId = bytesToUUID(resultSet.getBytes("wallet_id"));
        UUID customerId = bytesToUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = bytesToUUID(resultSet.getBytes("voucher_id"));
        return new Wallet(walletId,customerId,voucherId);
    };

    private static UUID bytesToUUID(byte[] bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }

    public Optional<Wallet> saveWallet(Wallet wallet){
        var insertResult = jdbcTemplate.update("INSERT INTO wallet(wallet_id, customer_id, voucher_id) VALUES(UNHEX(REPLACE( :wallet_id, '-', '')),UNHEX(REPLACE( :customer_id, '-', '')),UNHEX(REPLACE( :voucher_id, '-', '')))",
                toParamMap(wallet));
        if (insertResult != 1){
            return Optional.empty();
        }
        return Optional.of(wallet);
    }

    public Optional<Wallet> getWalletWithCustomerAndVoucher(UUID customerId, UUID voucherId){
        try {
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("customer_id",customerId);
            paramMap.put("voucher_Id", voucherId);

            Wallet foundWallet = jdbcTemplate.queryForObject("SELECT * FROM wallet where customer_id = UNHEX(REPLACE( :customer_id, '-', '')) and voucher_id = UNHEX(REPLACE( :voucher_id, '-', ''))",
                    paramMap, walletRowMapper);
            return Optional.of(foundWallet);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Wallet> findWalletById(UUID walletId){
        try {
            Wallet foundWallet = jdbcTemplate.queryForObject("Select * from wallet where wallet_id = UNHEX(REPLACE( :wallet_id, '-', ''))",
                    Collections.singletonMap("wallet_id",walletId.toString().getBytes()),walletRowMapper);
            return Optional.of(foundWallet);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Wallet> findWalletByVoucherId(UUID voucherId){
        return jdbcTemplate.query("Select * from wallet where voucher_id = UNHEX(REPLACE( :voucher_id, '-', ''))",
                Collections.singletonMap("voucher_id",voucherId.toString().getBytes()),walletRowMapper);
    }

    public List<Wallet> findWalletByCustomerId(UUID customerId){
        return jdbcTemplate.query("Select * from wallet where customer_id = UNHEX(REPLACE( :customer_id, '-', ''))",
                Collections.singletonMap("customer_id",customerId.toString().getBytes()),walletRowMapper);
    }

    public List<Voucher> findVoucherByCustomerId(UUID customerId){
        return jdbcTemplate.query("Select * from vouchers where voucher_id in (Select voucher_id from wallet where customer_id = UNHEX(REPLACE( :customer_id, '-', '')))",
                Collections.singletonMap("customer_id",customerId.toString().getBytes()), JdbcVoucherRepository.voucherRowMapper);
    }

    public List<Customer> findCustomerByVoucherId(UUID voucherId){
        return jdbcTemplate.query("Select * from customers where customer_id in (Select customer_id from wallet where voucher_id = UNHEX(REPLACE( :voucher_id, '-', '')))",
                Collections.singletonMap("voucher_id",voucherId.toString().getBytes()), CustomerRepository.customerRowMapper);
    }

    public List<Wallet> getAllWallets(){
        return jdbcTemplate.query("Select * from wallet",Collections.emptyMap(),walletRowMapper);
    }

    public boolean deleteWallets(Wallet wallet){
        var deleteResult = jdbcTemplate.update("delete from wallet where wallet_id = UNHEX(REPLACE( :wallet_id, '-', ''))",toParamMap(wallet));
        if(deleteResult!=1)
            return false;
        return true;
    }
}
