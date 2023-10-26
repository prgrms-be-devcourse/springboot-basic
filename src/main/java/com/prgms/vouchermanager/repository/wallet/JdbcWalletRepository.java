package com.prgms.vouchermanager.repository.wallet;

import com.prgms.vouchermanager.domain.wallet.Wallet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgms.vouchermanager.repository.wallet.WalletQueryType.*;


@Repository
public class JdbcWalletRepository implements WalletRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcWalletRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public Wallet save(Wallet wallet) {

        SqlParameterSource forSequenceParam = new MapSqlParameterSource();
        Long sequence = template.queryForObject(COUNT.getQuery(), forSequenceParam, Long.class);
        wallet.setId(sequence + 1);

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", wallet.getId())
                .addValue("customer_id", wallet.getCustomer_id())
                .addValue("voucher_id", wallet.getVoucher_id().toString());

        template.update(INSERT.getQuery(), param);

        return wallet;
    }

    @Override
    public List<Wallet> findByCustomerId(Long customerId) {

        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customer_id", customerId);

        return template.query(SELECT_BY_CUSTOMER_ID.getQuery(), param, walletRowMapper());

    }

    @Override
    public Optional<Wallet> findByVoucherId(UUID voucherId) {
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucher_id", voucherId.toString());

        try {
            return Optional.of(template.queryForObject(SELECT_BY_VOUCHER_ID.getQuery(), param, walletRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void deleteByCustomerId(Long customerId) {
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customer_id", customerId);

        template.update(DELETE_BY_CUSTOMER_ID.getQuery(), param);

    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, count) -> {
            Long id = Long.parseLong(rs.getString("id"));
            Long customerId = Long.parseLong(rs.getString("customer_id"));
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));

            return new Wallet(id, customerId, voucherId);
        };
    }
}
