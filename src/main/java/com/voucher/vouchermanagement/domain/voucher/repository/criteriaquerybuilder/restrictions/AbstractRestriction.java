package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions;

public abstract class AbstractRestriction {
	private String columnName;

	public AbstractRestriction(String columnName) {
		this.columnName = columnName;
	}

	public abstract String makeExpression();
	public String getColumnName() {
		return columnName;
	}
}
