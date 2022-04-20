package com.prgrms.voucher_manager.wallet.repository;

import com.prgrms.voucher_manager.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("dev")
public class JdbcWalletRepository implements WalletRepository{

    private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

    private static final String INSERT_SQL = "INSERT INTO wallet(voucher_id, customer_id) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId))";
    private static final String UPDATE_BY_ID_SQL = "UPDATE wallet SET voucher_id = UUID_TO_BIN(:voucherId) WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:beforeVoucherId)";
    private static final String DELETE_ALL_SQL = "DELETE FROM wallet";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId) AND customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM wallet";
    private static final String SELECT_BY_CUSTOMER_ID_SQL = "SELECT * FROM wallet WHERE customer_id = UUID_TO_BIN(:customerId)";
    private static final String SELECT_BY_VOUCHER_ID_SQL = "SELECT * FROM wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private static final String SELECT_COUNT_ALL_SQL = "SELECT COUNT(*) FROM wallet";


    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        return Wallet.builder()
                .voucherId(voucherId)
                .customerId(customerId)
                .build();
    };


    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Wallet insert(Wallet wallet) {
        try {
            int update = jdbcTemplate.update(INSERT_SQL, toParamMap(wallet));
            if(update != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
        } catch (DataAccessException e) {
            logger.info("중복된 바우처가 이미 등록되어 있습니다.");
        }
        return wallet;
    }

    public Wallet update(Wallet wallet, UUID beforeId) {
        jdbcTemplate.update(UPDATE_BY_ID_SQL, toUpdateParamMap(wallet, beforeId));
        return null;
    }

    public Integer count() {
        return jdbcTemplate.queryForObject(SELECT_COUNT_ALL_SQL, Collections.emptyMap(),Integer.class);
    }

    public List<Wallet> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, walletRowMapper);
    }

    public List<Wallet> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER_ID_SQL, Collections.singletonMap("customerId", customerId.toString().getBytes()), walletRowMapper);
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query(SELECT_BY_VOUCHER_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString().getBytes()), walletRowMapper);
    }


//    public void deleteAll() {
//        jdbcTemplate.update(DELETE_ALL_SQL,Collections.emptyMap());
//    }

    public void deleteByVoucherId(Wallet wallet) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, toParamMap(wallet));
    }



    private Map<String, Object> toParamMap(Wallet wallet) {

        HashMap<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
        }};

        return hashMap;
    }
    private Map<String, Object> toUpdateParamMap(Wallet wallet, UUID voucherId) {

        HashMap<String, Object> hashMap = new HashMap<>() {{
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
            put("beforeVoucherId", voucherId.toString().getBytes());
        }};

        return hashMap;
    }
    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());

    }
}
