package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions;

public class SingleRestriction extends AbstractRestriction {

	private final String paramName;

	public SingleRestriction(String columnName, String paramName) {
		super(columnName);
		this.paramName = paramName;
	}

	public String makeExpression() {
		return getColumnName() + " = :" + paramName;
	}
}
