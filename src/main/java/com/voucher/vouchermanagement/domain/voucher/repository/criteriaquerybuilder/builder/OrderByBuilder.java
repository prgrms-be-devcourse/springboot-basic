package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.AbstractRestriction;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.OrderByRestriction;

public class OrderByBuilder {
	private final AbstractRestriction[] restrictions;

	public OrderByBuilder(AbstractRestriction[] restrictions) {
		this.restrictions = restrictions;
	}

	public static OrderByBuilder builder(AbstractRestriction... restrictions) {
		return new OrderByBuilder(restrictions);
	}

	public static OrderByRestriction orderBy(String columnName, boolean asc) {
		return new OrderByRestriction(columnName, asc);
	}

	public String build() {
			return " ORDER BY " + Arrays.stream(this.restrictions)
				.filter(restriction -> restriction != null)
				.peek(System.out::println)
				.map(restriction -> restriction.makeExpression())
				.collect(Collectors.joining(", "));
	}
}
