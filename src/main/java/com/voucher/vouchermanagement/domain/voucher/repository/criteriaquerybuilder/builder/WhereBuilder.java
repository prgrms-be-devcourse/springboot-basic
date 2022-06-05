package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.AbstractRestriction;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.BetweenRestriction;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.SingleRestriction;

public class WhereBuilder {

	private final AbstractRestriction[] restrictions;

	public WhereBuilder(AbstractRestriction[] restrictions) {
		this.restrictions = restrictions;
	}

	public static WhereBuilder builder(AbstractRestriction... restrictions) {
		return new WhereBuilder(restrictions);
	}
	public static AbstractRestriction single(String columnName, String paramName) {
		return new SingleRestriction(columnName, paramName);
	}

	public static AbstractRestriction between(String columnName, String lowerBoundParamName, String upperBoundParamName) {
		return new BetweenRestriction(columnName, lowerBoundParamName, upperBoundParamName);
	}

	public String build() {
		return Arrays.stream(this.restrictions)
			.filter(restriction -> restriction != null)
			.peek(System.out::println)
			.map(restriction -> restriction.makeExpression())
			.collect(Collectors.joining(" and "));

	}

}
