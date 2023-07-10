package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.VoucherWallet;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Profile({"db", "test"})
@Repository
public class JDBCVoucherWalletRepository implements VoucherWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCVoucherWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<VoucherWallet> voucherWalletRowMapper = (rs, rowNum) -> new VoucherWallet(UUID.fromString(rs.getString("customerId")), UUID.fromString(rs.getString("voucherId")));

    @Override
    public void addVoucherToWallet(VoucherWallet voucherWallet) {
        String sql = "INSERT INTO VoucherWallet(customerId, voucherId) values (?, ?)";
        jdbcTemplate.update(sql, voucherWallet.getCustomerId().toString(), voucherWallet.getVoucherId().toString());
    }

    @Override
    public void removeVoucherFromWallet(VoucherWallet voucherWallet) {
        String sql = "DELETE FROM VoucherWallet WHERE customerId = ? AND voucherId = ?";
        jdbcTemplate.update(sql, voucherWallet.getCustomerId().toString(), voucherWallet.getVoucherId().toString());
    }

    @Override
    public Set<VoucherWallet> findVoucherWalletsByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM VoucherWallet WHERE customerId = ?";
        return new HashSet<>(jdbcTemplate.query(sql, voucherWalletRowMapper, customerId.toString()));
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        String sql = "DELETE FROM VoucherWallet WHERE customerId = ?";
        jdbcTemplate.update(sql, customerId.toString());
    }

    @Override
    public Set<VoucherWallet> findVoucherWalletsByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM VoucherWallet WHERE voucherId = ?";
        return new HashSet<>(jdbcTemplate.query(sql, voucherWalletRowMapper, voucherId.toString()));
    }
}
