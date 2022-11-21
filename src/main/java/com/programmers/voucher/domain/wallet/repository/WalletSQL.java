package com.programmers.voucher.domain.wallet.repository;

public enum WalletSQL {

	INSERT("INSERT INTO wallets(voucher_id, customer_id, created_at) VALUES (:voucherId, :customerId, :createdAt)"),
	SELECT_VOUCHER_BY_CUSTOMER_ID(
		"SELECT v.* from wallets w INNER JOIN customers c ON w.customer_id = c.customer_id AND w.customer_id = :customerId INNER JOIN vouchers v ON w.voucher_id = v.voucher_id"),
	SELECT_CUSTOMER_BY_VOUCHER_ID(
		"SELECT c.* from wallets w INNER JOIN vouchers v ON w.voucher_id = v.voucher_id AND w.voucher_id = :voucherId INNER JOIN customers c ON w.customer_id = c.customer_id"),
	DELETE_BY_CUSTOMER_ID("DELETE FROM wallets WHERE customer_id = :customerId"),
	DELETE_ALL("DELETE FROM wallets");

	private final String sql;

	WalletSQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
