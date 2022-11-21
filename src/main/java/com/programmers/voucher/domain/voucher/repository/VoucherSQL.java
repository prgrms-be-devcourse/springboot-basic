package com.programmers.voucher.domain.voucher.repository;

public enum VoucherSQL {

	INSERT(
		"INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at) VALUES (:voucherId, :voucherType, :discount, :createdAt)"),
	SELECT_BY_ID("SELECT * FROM vouchers WHERE voucher_id = :voucherId"),
	SELECT_ALL("SELECT * FROM vouchers"),
	DELETE_BY_ID("DELETE FROM vouchers WHERE voucher_id = :voucherId"),
	DELETE_ALL("DELETE FROM vouchers");

	private final String sql;

	VoucherSQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
