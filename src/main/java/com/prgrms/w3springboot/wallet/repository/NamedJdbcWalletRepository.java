package com.prgrms.w3springboot.wallet.repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.w3springboot.customer.Customer;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.wallet.Wallet;

@Repository
@Profile("production")
public class NamedJdbcWalletRepository implements WalletRepository {
	private static final int SUCCESS = 1;
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final VoucherFactory voucherFactory = VoucherFactory.getInstance();
	private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
		long amount = resultSet.getLong("amount");
		VoucherType voucherType = VoucherType.of(resultSet.getString("type").toLowerCase());
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return voucherFactory.createVoucher(voucherId, amount, voucherType, createdAt);
	};
	private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		UUID customerId = toUUID(resultSet.getBytes("customer_id"));
		String name = resultSet.getString("name");
		String email = resultSet.getString("email");
		LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
			resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
		LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

		return new Customer(customerId, name, email, lastLoginAt, createdAt);
	};

	public NamedJdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	private Map<String, Object> toParamMap(Wallet wallet) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("walletId", wallet.getWalletId().toString().getBytes());
		paramMap.put("customerId", wallet.getCustomerId().toString().getBytes());
		paramMap.put("voucherId", wallet.getVoucherId().toString().getBytes());

		return paramMap;
	}

	@Override
	public Wallet insert(Wallet wallet) {
		int update = jdbcTemplate.update(
			"INSERT INTO wallets(wallet_id, customer_id, voucher_id) "
				+ "VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
			toParamMap(wallet));
		throwsExceptionIfNotSuccess(update);

		return wallet;
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query(
			"SELECT v.voucher_id AS voucher_id, amount, type, created_at "
				+ "FROM vouchers v "
				+ "JOIN wallets w "
				+ "ON w.voucher_id = v.voucher_id "
				+ "WHERE w.customer_id = UUID_TO_BIN(:customerId)",
			Collections.singletonMap("customerId", customerId.toString().getBytes()),
			voucherRowMapper);
	}

	@Override
	public void delete(UUID walletId) {
		int result = jdbcTemplate.update("DELETE FROM wallets WHERE wallet_id = UUID_TO_BIN(:walletId)",
			Collections.singletonMap("walletId", walletId.toString().getBytes()));
		throwsExceptionIfNotSuccess(result);
	}

	@Override
	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return jdbcTemplate.query(
			"SELECT c.customer_id AS customer_id, name, email, last_login_at, created_at "
				+ "FROM customers c "
				+ "JOIN wallets w "
				+ "ON c.customer_id = w.customer_id "
				+ "WHERE w.voucher_id = UUID_TO_BIN(:voucherId)",
			Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
			customerRowMapper);
	}

	private void throwsExceptionIfNotSuccess(int result) {
		if (result != SUCCESS) {
			throw new NoSuchElementException("입력된 바우처가 존재하지 않습니다.");
		}
	}
}
