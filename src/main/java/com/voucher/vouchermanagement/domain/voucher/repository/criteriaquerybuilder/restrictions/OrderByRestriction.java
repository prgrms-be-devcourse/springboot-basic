package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions;

public class OrderByRestriction extends AbstractRestriction {
	private String direction;
	public OrderByRestriction(String columnName, boolean asc) {
		super(columnName);
		this.direction = asc ? "ASC" : "DESC";
	}

	@Override
	public String makeExpression() {
		return getColumnName() +" " + direction;
	}
}
