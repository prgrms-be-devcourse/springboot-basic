package org.programmers.springboot.basic.domain.wallet.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class JdbcWalletRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wallet> walletRowMapper;

    @Autowired
    public JdbcWalletRepository(@Qualifier("mysqlDataSource") DataSource dataSource, WalletMapper walletMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.walletRowMapper = walletMapper.walletRowMapper();
    }

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallets (email, voucher_id) VALUES(?, UUID_TO_BIN(?))";
        this.jdbcTemplate.update(sql, wallet.getEmail(), wallet.getVoucherId().toString().getBytes());
    }

    @Override
    public List<Wallet> findByEmail(String email) {
        String sql = "SELECT * FROM wallets WHERE email = ?";
        return this.jdbcTemplate.query(sql, walletRowMapper, email);
    }

    @Override
    public List<Wallet> findById(UUID voucherId) {
        String sql = "SELECT * FROM wallets WHERE voucher_id = UUID_TO_BIN(?)";
        return this.jdbcTemplate.query(sql, walletRowMapper, voucherId.toString().getBytes());
    }

    @Override
    public Optional<Wallet> findByEmailAndId(String email, UUID voucherId) {
        String sql = "SELECT * FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, walletRowMapper, email, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No customer found for email and voucherId: {}, {}", email, voucherId);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public void delete(Wallet wallet) {
        String sql = "DELETE FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, wallet.getEmail(), wallet.getVoucherId().toString().getBytes());
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE TABLE wallets";
        this.jdbcTemplate.execute(sql);
    }
}
