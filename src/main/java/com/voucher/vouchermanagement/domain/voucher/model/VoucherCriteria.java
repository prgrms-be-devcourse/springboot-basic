package com.voucher.vouchermanagement.domain.voucher.model;

import javax.validation.Valid;

public class VoucherCriteria {

	private VoucherType type;

	@Valid
	private DateTimePeriod period;

	protected VoucherCriteria() {
	}

	public VoucherCriteria(VoucherType type, DateTimePeriod period) {
		this.type = type;
		this.period = period;
	}

	public VoucherType getType() {
		return type;
	}

	public DateTimePeriod getPeriod() {
		return period;
	}

	public void setType(VoucherType type) {
		this.type = type;
	}

	public void setPeriod(DateTimePeriod period) {
		this.period = period;
	}
}
