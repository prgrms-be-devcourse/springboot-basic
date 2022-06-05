package com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder;

import static org.apache.logging.log4j.util.Strings.isBlank;

import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder.OrderByBuilder;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.builder.WhereBuilder;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.AbstractRestriction;
import com.voucher.vouchermanagement.domain.voucher.repository.criteriaquerybuilder.restrictions.OrderByRestriction;

public class NamedParameterJdbcTemplateQueryBuilder {

	private String select;
	private String from;

	private String where;

	private String orderBy;

	private String limit;

	private String offset;

	private NamedParameterJdbcTemplateQueryBuilder(String select, String from) {
		this.select = select;
		this.from = from;
	}

	public static
	NamedParameterJdbcTemplateQueryBuilder builder(String select, String from) {
		return new NamedParameterJdbcTemplateQueryBuilder(select, from);
	}
	public NamedParameterJdbcTemplateQueryBuilder select(String s) {
		this.select = s;

		return this;
	}

	public NamedParameterJdbcTemplateQueryBuilder from(String s) {
		this.from = s;

		return this;
	}

	public NamedParameterJdbcTemplateQueryBuilder where(AbstractRestriction... restrictions) {
		this.where = WhereBuilder.builder(restrictions).build();

		return this;
	}

	public NamedParameterJdbcTemplateQueryBuilder orderBy(OrderByRestriction... restrictions) {
		this.orderBy = OrderByBuilder.builder(restrictions).build();

		return this;
	}

	public NamedParameterJdbcTemplateQueryBuilder limit(int limit) {
		this.limit = " LIMIT " + limit;

		return this;
	}

	public NamedParameterJdbcTemplateQueryBuilder offset(long offset) {
		this.offset = " OFFSET " + offset;

		return this;
	}

	public String build() {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append(this.select);
		builder.append(" FROM ");
		builder.append(this.from);
		if(!isBlank(this.where)) {
			builder.append(" WHERE ");
			builder.append(this.where);
		}
		if(!isBlank(this.orderBy)) {
			builder.append(this.orderBy);
		}
		if (!isBlank(this.limit)) {
			builder.append(this.limit);
		}
		if (!isBlank(this.offset)) {
			builder.append(this.offset);
		}
		return builder.toString();
	}
}

