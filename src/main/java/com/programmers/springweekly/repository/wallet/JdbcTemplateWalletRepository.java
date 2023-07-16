package com.programmers.springweekly.repository.wallet;

import com.programmers.springweekly.domain.wallet.Wallet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
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

        try {
            template.update(sql, param);
            return wallet;
        } catch (DuplicateKeyException e) {
            log.warn("이미 있는 바우처 지갑 ID입니다.", e);
            throw new DuplicateKeyException("이미 있는 바우처 지갑 ID입니다. 관리자에게 문의해주세요.");
        }
    }

    @Override
    public Optional<Wallet> findByCustomerId(UUID customerId) {
        String sql = "select * from wallet where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        try {
            Wallet wallet = template.queryForObject(sql, param, walletRowMapper());
            return Optional.of(wallet);
        } catch (EmptyResultDataAccessException e) {
            log.warn("고객의 ID로 할당된 바우처가 없을 때 예외 발생, Optional Empty로 반환", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "select * from wallet where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());

        return template.query(sql, param, walletRowMapper());
    }

    @Override
    public int deleteByWalletId(UUID walletId) {
        String sql = "delete from wallet where wallet_id = :walletId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", walletId.toString());

        return template.update(sql, param);
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "select * from wallet";

        List<Wallet> walletList = template.query(sql, walletRowMapper());

        return walletList;
    }

    @Override
    public boolean existByWalletId(UUID walletId) {
        String sql = "select * from wallet where wallet_id = :walletId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("walletId", walletId.toString());

        try {
            template.queryForObject(sql, param, walletRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("바우처 지갑 ID가 존재하는지 체크했으나 없어서 예외 발생", e);
            return false;
        }
    }

    private RowMapper<Wallet> walletRowMapper() {
        return ((resultSet, rowMap) -> Wallet.builder()
                .walletId(UUID.fromString(resultSet.getString("wallet_id")))
                .customerId(UUID.fromString(resultSet.getString("customer_id")))
                .voucherId(UUID.fromString(resultSet.getString("voucher_id")))
                .build()
        );
    }

}
