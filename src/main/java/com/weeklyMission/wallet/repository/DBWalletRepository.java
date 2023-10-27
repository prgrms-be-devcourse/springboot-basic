package com.weeklyMission.wallet.repository;

import com.weeklyMission.wallet.domain.Wallet;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class DBWalletRepository implements WalletRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID walletId = toUUID(resultSet.getBytes("wallet_id"));
        UUID memberId = toUUID(resultSet.getBytes("member_id"));
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));

        return new Wallet(walletId, memberId, voucherId);
    };

    private Map<String, Object> toParamMap(Wallet wallet){
        Map<String, Object> map = new HashMap<>();
        map.put("walletId", wallet.walletId().toString().getBytes());
        map.put("memberId", wallet.memberId().toString().getBytes());
        map.put("voucherId", wallet.voucherId().toString().getBytes());
        return map;
    }

    @Override
    public void save(Wallet wallet) {
        jdbcTemplate.update(
            "INSERT INTO wallet (wallet_id, member_id, voucher_id) VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:memberId), UUID_TO_BIN(:voucherId))",
            toParamMap(wallet));
    }

    @Override
    public List<Wallet> findByMemberId(UUID memberId) {
        return jdbcTemplate.query("select * from wallet where member_id = UUID_TO_BIN(:memberId)",
            Collections.singletonMap("memberId", memberId.toString().getBytes()), walletRowMapper);
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("select * from wallet where voucher_id = UUID_TO_BIN(:voucherId)",
            Collections.singletonMap("voucherId", voucherId.toString().getBytes()), walletRowMapper);
    }

    @Override
    public void deleteById(UUID memberId, UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId.toString().getBytes());
        paramMap.put("voucherId", voucherId.toString().getBytes());

        jdbcTemplate.update("delete from wallet where member_id = UUID_TO_BIN(:memberId) and voucher_id = UUID_TO_BIN(:voucherId)",
            paramMap);
    }


    static UUID toUUID(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
