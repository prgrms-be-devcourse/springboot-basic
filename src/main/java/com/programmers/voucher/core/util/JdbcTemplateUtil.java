package com.programmers.voucher.core.util;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;
import com.programmers.voucher.domain.wallet.model.Wallet;

public class JdbcTemplateUtil {

	public static UUID toUUID(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}

	public static Map<String, Object> toVoucherParamMap(Voucher voucher) {
		return Map.of(
			"voucherId", voucher.getVoucherId().toString(),
			"voucherType", voucher.getVoucherType().name(),
			"discount", voucher.getDiscount(),
			"createdAt", voucher.getCreatedAt()
		);
	}

	public static Map<String, Object> toVoucherIdMap(UUID voucherId) {
		return Collections.singletonMap("voucherId", voucherId.toString());
	}

	public static RowMapper<Voucher> voucherRowMapper = (ResultSet rs, int rowNum) -> {
		UUID voucherId = toUUID(rs.getBytes("voucher_id"));
		String discount = String.valueOf(rs.getDouble("discount"));
		VoucherType voucherType = VoucherType.getVoucherType(rs.getString("voucher_type"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		return VoucherFactory.createVoucher(voucherId, voucherType, discount, createdAt);
	};

	public static Map<String, Object> toCustomerParamMap(Customer customer) {
		return Map.of(
			"customerId", customer.getCustomerId().toString(),
			"customerType", customer.getCustomerType().name(),
			"createdAt", customer.getCreatedAt(),
			"lastModifiedAt", customer.getLastModifiedAt()
		);
	}

	public static Map<String, Object> toCustomerIdMap(UUID customerId) {
		return Collections.singletonMap("customerId", customerId.toString());
	}

	public static RowMapper<Customer> customerRowMapper = (ResultSet rs, int rowNum) -> {
		UUID customerId = toUUID(rs.getBytes("customer_id"));
		LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
		CustomerType customerType = CustomerType.getCustomerType(rs.getString("customer_type"));
		LocalDateTime lastModifiedAt = rs.getObject("last_modified_at", LocalDateTime.class);
		return new Customer(customerId, createdAt, customerType, lastModifiedAt);
	};

	public static Map<String, Object> toWalletParamMap(Wallet wallet) {
		return Map.of(
			"voucherId", wallet.getVoucher().getVoucherId().toString(),
			"customerId", wallet.getCustomer().getCustomerId().toString(),
			"createdAt", wallet.getCreatedAt()
		);
	}
}
