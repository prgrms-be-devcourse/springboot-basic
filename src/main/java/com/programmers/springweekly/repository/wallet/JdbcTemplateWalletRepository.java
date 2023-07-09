package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class JdbcTemplateWalletRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateWalletRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "insert into wallet values(:walletId, :customerId, :voucherId)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", wallet.getWalletId().toString())
                .addValue("customerId", wallet.getCustomerId().toString())
                .addValue("voucherId", wallet.getVoucherId().toString());

        template.update(sql, param);

        return wallet;
    }

    @Override
    public Wallet findByCustomerId(UUID customerId) {
        String sql = "select * from wallet where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        try {
            return template.queryForObject(sql, param, walletRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("해당 고객에게 할당된 바우처가 없습니다.");
            throw new NoSuchElementException("해당 고객에게 할당된 바우처가 없습니다.");
        }
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "select * from wallet where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());

        List<Wallet> walletList = template.query(sql, param, walletRowMapper());

        if (walletList.isEmpty()) {
            log.error("이 바우처는 현재 할당된 고객이 없습니다.");
            throw new NoSuchElementException("이 바우처는 현재 할당된 고객이 없습니다.");
        }

        return template.query(sql, param, walletRowMapper());
    }

    @Override
    public void deleteByWalletId(UUID walletId) {
        String sql = "delete from wallet where wallet_id = :walletId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", walletId.toString());

        try {
            template.update(sql, param);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
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
