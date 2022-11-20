package com.programmers.voucher.domain.wallet.repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;
import com.programmers.voucher.domain.wallet.model.Wallet;
import com.programmers.voucher.exception.DataUpdateException;
import com.programmers.voucher.exception.ExceptionMessage;

@Repository
@Profile({"jdbc", "test"})
public class JdbcWalletRepository implements WalletRepository {

	private static final Logger log = LoggerFactory.getLogger(JdbcWalletRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public JdbcWalletRepository(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Wallet save(Wallet wallet) {
		int save = jdbcTemplate.update(
			"INSERT INTO wallets(voucher_id, customer_id, created_at) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), :createdAt)",
			toParamMap(wallet));

		if (save != 1) {
			log.error(ExceptionMessage.DATA_UPDATE_FAIL.getMessage());
			throw new DataUpdateException();
		}
		return wallet;
	}

	@Override
	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return jdbcTemplate.query(
			"SELECT v.* from wallets w INNER JOIN customers c ON w.customer_id = c.customer_id AND w.customer_id = UUID_TO_BIN(:customerId) INNER JOIN vouchers v ON w.voucher_id = v.voucher_id",
			Collections.singletonMap("customerId", customerId.toString()), voucherRowMapper);
	}

	@Override
	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return jdbcTemplate.query(
			"SELECT c.* from wallets w INNER JOIN vouchers v ON w.voucher_id = v.voucher_id AND w.voucher_id = UUID_TO_BIN(:voucherId) INNER JOIN customers c ON w.customer_id = c.customer_id",
			Collections.singletonMap("voucherId", voucherId.toString()), customerRowMapper);
	}

	@Override
	public void deleteByCustomerId(UUID customerId) {
		jdbcTemplate.update("DELETE FROM wallets WHERE customer_id = :customerId",
			Collections.singletonMap("customerId", customerId));
	}

	private Map<String, Object> toParamMap(Wallet wallet) {
		return Map.of(
			"voucherId", wallet.getVoucher().getVoucherId().toString(),
			"customerId", wallet.getCustomer().getCustomerId().toString(),
			"createdAt", wallet.getCreatedAt()
		);
	}

	private RowMapper<Voucher> voucherRowMapper = (rs, rowNum) -> {
		UUID voucherId = toUUID(rs.getBytes("voucher_id"));
		String discount = String.valueOf(rs.getDouble("discount"));
		VoucherType voucherType = VoucherType.getVoucherType(rs.getString("voucher_type"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		return VoucherFactory.createVoucher(voucherId, voucherType, discount, createdAt);
	};

	private RowMapper<Customer> customerRowMapper = (ResultSet rs, int rowNum) -> {
		UUID customerId = toUUID(rs.getBytes("customer_id"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		CustomerType customerType = CustomerType.getCustomerType(rs.getString("customer_type"));
		LocalDateTime lastModifiedAt = rs.getObject("last_modified_at", LocalDateTime.class);
		return new Customer(customerId, createdAt, customerType, lastModifiedAt);
	};

	private UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}
