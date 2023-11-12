package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.wallet.Wallet;

@Repository
public class JdbcWalletRepository implements WalletRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Wallet> walletRowMapper = (resultSet, rowNum) -> new Wallet(
		resultSet.getLong("wallet_id"),
		UUID.fromString(resultSet.getString("customer_id")),
		UUID.fromString(resultSet.getString("voucher_id"))
	);

	public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insertVoucherForCustomer(UUID customerId, UUID voucherId) {
		String sql = "INSERT INTO wallet (customer_id, voucher_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
	}

	@Override
	public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId) {
		String sql = "DELETE FROM wallet WHERE customer_id = ? AND voucher_id = ?";
		jdbcTemplate.update(sql, customerId.toString(), voucherId.toString());
	}

	@Override
	public List<Wallet> findByCustomerId(UUID customerId) {
		String sql = "SELECT * FROM wallet WHERE customer_id = ?";
		return jdbcTemplate.query(sql, walletRowMapper, customerId.toString());
	}

	@Override
	public List<Wallet> findByVoucherId(UUID voucherId) {
		String sql = "SELECT * FROM wallet WHERE voucher_id = ?";
		return jdbcTemplate.query(sql, walletRowMapper, voucherId.toString());
	}

}
