package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions;

public class BetweenRestriction extends AbstractRestriction {
	private final String lowerBoundParamName;
	private final String upperBoundParamName;

	public BetweenRestriction(String columnName, String lowerBoundParamName, String upperBoundParamName) {
		super(columnName);
		this.lowerBoundParamName = lowerBoundParamName;
		this.upperBoundParamName = upperBoundParamName;
	}

	@Override
	public String makeExpression() {

		return getColumnName() +" between :" + lowerBoundParamName +" and :" + upperBoundParamName;
	}
}
