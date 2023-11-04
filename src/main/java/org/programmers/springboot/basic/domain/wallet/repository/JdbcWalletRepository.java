package org.programmers.springboot.basic.domain.wallet.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;
import org.programmers.springboot.basic.util.converter.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDConverter.toUUID(rs.getBytes("voucher_id"));
            Email email = Email.valueOf(rs.getString("email"));
            return new Wallet(voucherId, email);
        };
    }
    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallets (email, voucher_id) VALUES(?, UUID_TO_BIN(?))";
        this.jdbcTemplate.update(sql, wallet.getEmail().getEmail(), UUIDConverter.toBytes(wallet.getVoucherId()));
    }

    @Override
    public List<Wallet> findByEmail(Email email) {
        String sql = "SELECT * FROM wallets WHERE email = ?";
        return this.jdbcTemplate.query(sql, walletRowMapper(), email.getEmail());
    }

    @Override
    public List<Wallet> findById(UUID voucherId) {
        String sql = "SELECT * FROM wallets WHERE voucher_id = UUID_TO_BIN(?)";
        return this.jdbcTemplate.query(sql, walletRowMapper(), (Object) UUIDConverter.toBytes(voucherId));
    }
    @Override
    public Optional<Wallet> findByEmailNId(Email email, UUID voucherId) {
        String sql = "SELECT * FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(sql, walletRowMapper(),
                    email.getEmail(), UUIDConverter.toBytes(voucherId)));
        } catch (EmptyResultDataAccessException e) {
            log.warn("No customer found for email and voucherId: {}, {}", email.getEmail(), voucherId);
        } catch (DataAccessException e) {
            log.error("Data access exception: {}", e.toString());
        }
        return Optional.empty();
    }

    @Override
    public void delete(Wallet wallet) {
        String sql = "DELETE FROM wallets WHERE email = ? AND voucher_id = UUID_TO_BIN(?)";
        this.jdbcTemplate.update(sql, wallet.getEmail().getEmail(), UUIDConverter.toBytes(wallet.getVoucherId()));
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE TABLE wallets";
        this.jdbcTemplate.execute(sql);
    }
}
