package com.prgrms.vouchermanagement.wallet;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.prgrms.vouchermanagement.customer.domain.Customer;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Repository
public class WalletRepository {
	private final JdbcTemplate jdbcTemplate;
	private final Logger logger = LoggerFactory.getLogger(WalletRepository.class);

	public WalletRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		String customerName = resultSet.getString("name");
		String email = resultSet.getString("email");
		UUID customerId = toUUID(resultSet.getBytes("customer_id"));
		LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
			resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
	};

	private static RowMapper<Voucher> voucherRowMapper = (rs, i) -> {
		UUID voucher_id = toUUID(rs.getBytes("voucher_id"));
		long discount_info = rs.getLong("discount_info");
		LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
		VoucherType type = VoucherType.of(rs.getInt("type"));

		return type.getVoucher(voucher_id, discount_info, createdAt);
	};

	protected Optional<Voucher> findVoucherOfCustomer(UUID customerId, UUID voucherId) {
		try {
			return Optional.ofNullable(
				jdbcTemplate.queryForObject(
					"select v.* from customer_has_vouchers as cv join vouchers as v on cv.voucher_id = v.voucher_id WHERE cv.customer_id = UUID_TO_BIN(?) and cv.voucher_id = UUID_TO_BIN(?)",
					voucherRowMapper, customerId.toString().getBytes(), voucherId.toString().getBytes()));
		} catch (EmptyResultDataAccessException e) {
			logger.info("customerId {} 와 매핑되는 voucherId {} 가 존재하지 않습니다", customerId, voucherId);

			return Optional.empty();
		}
	}

	static UUID toUUID(byte[] bytes) {
		var byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public List<Voucher> findAllVouchersOfCustomer(UUID customerId) {
		return jdbcTemplate.query(
			"select v.* from customer_has_vouchers as cv join vouchers as v on cv.voucher_id = v.voucher_id",
			voucherRowMapper);
	}

	public long deleteRecord(UUID customerId, UUID voucherId) {
		return jdbcTemplate.update(
			"DELETE FROM customer_has_vouchers where customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)",
			customerId.toString().getBytes(), voucherId.toString().getBytes());
	}

	public int insertRecord(UUID customerId, UUID voucherId) {
		return jdbcTemplate.update(
			"INSERT INTO customer_has_vouchers(id,customer_id, voucher_id) VALUES (UUID_TO_BIN(UUID()), UUID_TO_BIN(?), UUID_TO_BIN(?))",
			customerId.toString().getBytes(),
			voucherId.toString().getBytes()
		);
	}

	public List<Customer> findAllCustomersOfVoucher(UUID voucherId) {
		return jdbcTemplate.query(
			"select c.* from customer_has_vouchers as cv join customers as c on cv.voucher_id = c.customer_id",
			customerRowMapper);
	}
}
