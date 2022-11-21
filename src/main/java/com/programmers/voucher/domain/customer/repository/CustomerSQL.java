package com.programmers.voucher.domain.customer.repository;

public enum CustomerSQL {

	INSERT(
		"INSERT INTO customers(customer_id, customer_type, created_at, last_modified_at) VALUES (:customerId, :customerType, :createdAt, :lastModifiedAt)"),
	SELECT_BY_ID("SELECT * FROM customers WHERE customer_id = :customerId"),
	SELECT_ALL("SELECT * FROM customers"),
	SELECT_ALL_BLACKLIST("SELECT * FROM customers WHERE customer_type = :customerType"),
	UPDATE(
		"UPDATE customers SET customer_type = :customerType, last_modified_at = :lastModifiedAt WHERE customer_id = :customerId"),
	DELETE_BY_ID("DELETE FROM customers WHERE customer_id = :customerId"),
	DELETE_ALL("DELETE FROM customers");

	private final String sql;

	CustomerSQL(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
