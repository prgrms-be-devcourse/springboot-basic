package org.prgrms.springorder.repository.voucher;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class WalletRepository {

	private final Logger log = LoggerFactory.getLogger(WalletRepository.class);
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public WalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		var voucherId = toUUID(resultSet.getBytes("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		var voucherType = VoucherType.getVoucherByName(resultSet.getString("voucher_type"));
		return VoucherFactory.createVoucher(voucherType, voucherId, value,createAt);
	};

	private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		var customerId = toUUID(resultSet.getBytes("customer_id"));
		var customerName = resultSet.getString("customer_name");
		var email = resultSet.getString("email");
		var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
		var customerType = CustomerType.getCustomerTypeByRating(resultSet.getString("customer_type"));
		return new Customer(customerId, customerName, email, createdAt, customerType);
	};

	public Optional<Customer> findByVoucherId(UUID voucherId) {
		try {
			return Optional.ofNullable(jdbcTemplate.queryForObject(
				"SELECT * FROM customer INNER JOIN voucher on customer.customer_id = voucher.customer_id WHERE voucher.voucher_id = uuid_to_bin(:voucherId)",
				Collections.singletonMap("voucherId", voucherId.toString().getBytes()), customerRowMapper));
		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
			return Optional.empty();
		}

	}

	public void updateCustomerIdByVoucherId(UUID customerId, UUID voucherId) {
		Map<String, Object> paramMap = toIdParamMap(customerId, voucherId);
		jdbcTemplate.update(
			"UPDATE voucher SET customer_id = UUID_TO_BIN(:customerId) WHERE voucher_id = uuid_to_bin(:voucherId)",
			paramMap);
	}

	private Map<String, Object> toIdParamMap(UUID customerId, UUID voucherId) {
		return new HashMap<>() {{
			put("customerId", customerId.toString().getBytes());
			put("voucherId", voucherId.toString().getBytes());
		}};
	}

	public List<Voucher> findVouchersByCustomerId(UUID customerId) {

		try {
			return jdbcTemplate.query(
				"SELECT * FROM voucher INNER JOIN customer on voucher.customer_id = customer.customer_id "
					+ "WHERE voucher.customer_id = UUID_TO_BIN(:customerId)",
				Collections.singletonMap("customerId", customerId.toString()), voucherRowMapper);
		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
			return List.of();
		}

	}

	public void delete(UUID customerId, UUID voucherId) {
		Map<String, Object> paramMap = toIdParamMap(customerId, voucherId);
		try {
			jdbcTemplate.update(
				"DELETE FROM voucher Using voucher INNER JOIN customer on voucher.customer_id = customer.customer_id "
					+ "WHERE voucher.customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)",
				paramMap);
		} catch (DataAccessException e) {
			log.error(ErrorMessage.DATA_ACCESS_MESSAGE.toString());
		}

	}

}
