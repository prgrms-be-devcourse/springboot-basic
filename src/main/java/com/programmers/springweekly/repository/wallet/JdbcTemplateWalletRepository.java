package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcTemplateWalletRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateWalletRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "insert into values(:walletId, :customerId, :voucherId)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", wallet.getWalletId())
                .addValue("customerId", wallet.getCustomerId())
                .addValue("voucherId", wallet.getCustomerId());

        template.update(sql, param);

        return wallet;
    }

    @Override
    public Wallet findByCustomerId(String customerId) {
        String sql = "select * from wallet where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId);

        return template.queryForObject(sql, param, walletRowMapper());
    }

    @Override
    public List<Wallet> findByVoucherId(String voucherId) {
        String sql = "select * from wallet where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        return template.query(sql, param, walletRowMapper());
    }

    @Override
    public void deleteByWalletId(String walletId) {
        String sql = "delete from wallet where wallet_id = :walletId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", walletId);

        template.update(sql, param);
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "select * from wallet";

        return template.query(sql, walletRowMapper());
    }

    private RowMapper<Wallet> walletRowMapper() {
        return ((resultSet, rowMap) -> new Wallet(
                UUID.fromString(resultSet.getString("wallet_id")),
                UUID.fromString(resultSet.getString("customer_id")),
                UUID.fromString(resultSet.getString("voucher_id"))
        ));
    }
}
