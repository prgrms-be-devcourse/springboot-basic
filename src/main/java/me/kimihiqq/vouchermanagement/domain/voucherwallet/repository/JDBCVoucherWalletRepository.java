package me.kimihiqq.vouchermanagement.domain.voucherwallet.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
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

    private RowMapper<UUID> voucherIdRowMapper = (rs, rowNum) -> UUID.fromString(rs.getString("voucherId"));

    @Override
    public void addVoucherToWallet(UUID customerId, UUID voucherId) {
        String sql = "INSERT INTO VoucherWallet(customerId, voucherId) values (?, ?)";
        jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }

    @Override
    public void removeVoucherFromWallet(UUID customerId, UUID voucherId) {
        String sql = "DELETE FROM VoucherWallet WHERE customerId = ? AND voucherId = ?";
        jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
    }

    @Override
    public Set<UUID> findVoucherIdsByCustomerId(UUID customerId) {
        String sql = "SELECT voucherId FROM VoucherWallet WHERE customerId = ?";
        List<UUID> voucherIds = jdbcTemplate.query(sql, voucherIdRowMapper, customerId.toString());
        return new HashSet<>(voucherIds);
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }
    @Override
    public Set<UUID> findCustomerIdsByVoucherId(UUID voucherId) {
        String sql = "SELECT customerId FROM VoucherWallet WHERE voucherId = ?";
        List<UUID> customerIds = jdbcTemplate.query(sql, new Object[]{voucherId.toString()}, (rs, rowNum) -> UUID.fromString(rs.getString("customerId")));
        return new HashSet<>(customerIds);
    }

}