package com.weeklyMission.wallet.repository;

import com.weeklyMission.wallet.domain.Wallet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"prod", "dev"})
public class DBWalletRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        String walletId = resultSet.getString("wallet_id");
        String memberId = resultSet.getString("member_id");
        String voucherId = resultSet.getString("voucher_id");

        return new Wallet(walletId, memberId, voucherId);
    };

    private Map<String, Object> toParamMap(Wallet wallet) {
        Map<String, Object> map = new HashMap<>();
        map.put("walletId", wallet.walletId());
        map.put("memberId", wallet.memberId());
        map.put("voucherId", wallet.voucherId());
        return map;
    }

    @Override
    public Wallet save(Wallet wallet) {
        jdbcTemplate.update(
            "INSERT INTO wallet (wallet_id, member_id, voucher_id) VALUES (:walletId, :memberId, :voucherId)",
            toParamMap(wallet));

        return wallet;
    }

    @Override
    public List<Wallet> findByMemberId(String memberId) {
        return jdbcTemplate.query("select * from wallet where member_id = :memberId",
            Collections.singletonMap("memberId", memberId), walletRowMapper);
    }

    @Override
    public List<Wallet> findByVoucherId(String voucherId) {
        return jdbcTemplate.query("select * from wallet where voucher_id = :voucherId",
            Collections.singletonMap("voucherId", voucherId), walletRowMapper);
    }

    @Override
    public void deleteById(String memberId, String voucherId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("voucherId", voucherId);
        jdbcTemplate.update(
            "delete from wallet where member_id = :memberId and voucher_id = :voucherId",
            paramMap);
    }

    @Override
    public Boolean checkWallet(Wallet wallet) {
        return jdbcTemplate.queryForObject(
            "select exists(select * from wallet where member_id = :memberId and voucher_id = :voucherId)",
            toParamMap(wallet), Boolean.class);
    }
}
