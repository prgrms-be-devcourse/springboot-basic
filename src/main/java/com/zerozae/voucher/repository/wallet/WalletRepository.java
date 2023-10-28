package com.zerozae.voucher.repository.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import com.zerozae.voucher.exception.ErrorMessage;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.util.UuidConverter.toUUID;

@Repository
public class WalletRepository {

    private static final String CUSTOMER_ID = "customerId";
    private static final String VOUCHER_ID = "voucherId";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(customerId, voucherId);
    };

    public WalletRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Wallet save(Wallet wallet) {
        int insert = namedParameterJdbcTemplate.update(
                "INSERT INTO wallets(customer_id, voucher_id) VALUES(UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                toParamMap(wallet));

        if(insert != 1){
            throw ErrorMessage.error("저장에 실패했습니다.");
        }
        return wallet;
    }

    public Optional<Wallet> findWallet(UUID customerId, UUID voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "SELECT * FROM wallets WHERE customer_id = UUID_TO_BIN(:customerId) and voucher_id = UUID_TO_BIN(:voucherId) and deleted = 'N'",
                    Map.of(
                            CUSTOMER_ID, customerId.toString().getBytes(),
                            VOUCHER_ID,voucherId.toString().getBytes()),
                    walletRowMapper));
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Wallet> findByCustomerId(UUID customerId) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM wallets WHERE customer_id = UUID_TO_BIN(:customerId) and deleted = 'N'",
                Map.of(CUSTOMER_ID, customerId.toString().getBytes()),
                walletRowMapper);
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return namedParameterJdbcTemplate.query(
                "SELECT * FROM wallets WHERE voucher_id = UUID_TO_BIN(:voucherId) and deleted = 'N'",
                Map.of(VOUCHER_ID, voucherId.toString().getBytes()),
                walletRowMapper);
    }

    public void deleteByAllId(UUID customerId, UUID voucherId) {
        namedParameterJdbcTemplate.update(
                "UPDATE wallets SET deleted = 'Y' WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)",
                Map.of(
                        CUSTOMER_ID, customerId.toString().getBytes(),
                        VOUCHER_ID, voucherId.toString().getBytes()));
    }

    public void deleteAll(){
        namedParameterJdbcTemplate.getJdbcOperations().update("UPDATE wallets SET deleted = 'Y'");
    }

    private MapSqlParameterSource toParamMap(Wallet wallet) {
        return new MapSqlParameterSource()
                .addValue(CUSTOMER_ID, wallet.customerId().toString().getBytes())
                .addValue(VOUCHER_ID, wallet.voucherId().toString().getBytes());
    }
}
