package com.programmers.springbasic.repository.wallet;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.programmers.springbasic.entity.customer.Customer;
import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

@Repository
public class JdbcWalletRepository implements WalletRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> new Customer(
		UUID.fromString(resultSet.getString("customer_id")),
		resultSet.getString("name"),
		resultSet.getString("email"),
		resultSet.getTimestamp("created_at").toLocalDateTime()
	); // todo : nullable value 체크

	private final RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
		UUID id = UUID.fromString(rs.getString("voucher_id"));
		String type = rs.getString("voucher_type");

		if (VoucherType.PERCENT_DISCOUNT.name().equals(type)) {
			return new PercentDiscountVoucher(id, rs.getLong("percent"));
		} else if (VoucherType.FIXED_AMOUNT.name().equals(type)) {
			return new FixedAmountVoucher(id, rs.getLong("amount"));
		}

		throw new IllegalArgumentException("Unknown voucher type");
	};

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
