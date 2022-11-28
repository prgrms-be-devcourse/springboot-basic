package org.prgrms.springorder.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.domain.wallet.Wallet;
import org.springframework.jdbc.core.RowMapper;

public class JdbcUtil {

	public static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
		var voucherId = UUID.fromString(resultSet.getString("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var createAt = resultSet.getTimestamp("voucher_created_at").toLocalDateTime();
		var voucherType = VoucherType.getVoucherByName(resultSet.getString("voucher_type"));
		return VoucherFactory.createVoucher(voucherType, voucherId, value, createAt);
	};

	public static Map<String, Object> toParamMap(Voucher voucher) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("voucherId", voucher.getVoucherId().toString());
		paramMap.put("value", voucher.getValue());
		paramMap.put("voucherCreatedAt", voucher.getCreatedAt());
		paramMap.put("voucherType", voucher.getVoucherType().getName());
		return paramMap;
	}

	public static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
		var customerId = UUID.fromString(resultSet.getString("customer_id"));
		var customerName = resultSet.getString("customer_name");
		var email = resultSet.getString("email");
		var createdAt = resultSet.getTimestamp("customer_created_at").toLocalDateTime();
		var customerType = CustomerType.getCustomerTypeByRating(resultSet.getString("customer_type"));
		return new Customer(customerId, customerName, email, createdAt, customerType);
	};

	public static Map<String, Object> toParamMap(Customer customer) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("customerId", customer.getCustomerId().toString());
		paramMap.put("name", customer.getName());
		paramMap.put("email", customer.getEmail());
		paramMap.put("customerCreatedAt", Timestamp.valueOf(customer.getCustomerCreatedAt()));
		paramMap.put("customerType", customer.getCustomerType().getRating());
		return paramMap;
	}

	public static Map<String, Object> toUpdateParamMap(Voucher voucher) {
		Map<String, Object> updateParamMap = new HashMap<>();
		updateParamMap.put("voucherId", voucher.getVoucherId().toString());
		updateParamMap.put("value", voucher.getValue());
		return updateParamMap;
	}

	public static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
		var walletId = UUID.fromString(resultSet.getString("wallet_id"));

		var voucherId = UUID.fromString(resultSet.getString("voucher_id"));
		var value = resultSet.getInt("voucher_value");
		var voucherCreatedAt = resultSet.getTimestamp("voucher_created_at").toLocalDateTime();
		var voucherType = VoucherType.getVoucherByName(resultSet.getString("voucher_type"));

		var customerId = UUID.fromString(resultSet.getString("customer_id"));
		var customerName = resultSet.getString("customer_name");
		var email = resultSet.getString("email");
		var customerCreatedAt = resultSet.getTimestamp("customer_created_at").toLocalDateTime();
		var customerType = CustomerType.getCustomerTypeByRating(resultSet.getString("customer_type"));

		var walletCreatedAt = resultSet.getTimestamp("wallet_created_at").toLocalDateTime();
		return new Wallet(walletId, VoucherFactory.createVoucher(voucherType, voucherId, value, voucherCreatedAt),
			new Customer(customerId, customerName, email, customerCreatedAt, customerType), walletCreatedAt);
	};

	public static Map<String, Object> toParamMap(Wallet wallet) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("walletId", wallet.getWalletId().toString());
		paramMap.put("customerId", wallet.getCustomer().getCustomerId().toString());
		paramMap.put("voucherId", wallet.getVoucher().getVoucherId().toString());
		paramMap.put("walletCreatedAt", Timestamp.valueOf(wallet.getWalletCreatedAt()));
		return paramMap;
	}

}
