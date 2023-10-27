package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcWalletRepository implements WalletRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insertVoucherForCustomer(UUID customerId, UUID voucherId) {
		String sql = "INSERT INTO wallet (customer_id, voucher_id) VALUES (?, ?)";
		jdbcTemplate.update(sql, customerId.toString(), voucherId);
	}

	public void deleteVoucherFromCustomer(UUID customerId, UUID voucherId) {
		String sql = "DELETE FROM wallet WHERE customer_id = ? AND voucher_id = ?";
		jdbcTemplate.update(sql, customerId.toString(), voucherId);
	}

	public List<UUID> findVoucherIdsByCustomerId(UUID customerId) {
		String sql = "SELECT voucher_id FROM wallet WHERE customer_id = ?";
		return jdbcTemplate.query(sql, (rs, rowNum) -> UUID.fromString(rs.getString("voucher_id")), customerId);
	}

	public List<UUID> findCustomerIdsByVoucherId(UUID voucherId) {
		String sql = "SELECT customer_id FROM wallet WHERE voucher_id = ?";
		return jdbcTemplate.query(sql, (rs, rowNum) -> UUID.fromString(rs.getString("customer_id")), voucherId);
	}

}
