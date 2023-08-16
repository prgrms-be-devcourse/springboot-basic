package org.prgrms.kdt.model.domain;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.entity.VoucherEntity;

public abstract class Voucher {
	protected final Long voucherId;
	protected final int amount;
	protected final VoucherType voucherType;

	public Voucher(Long voucherId, int amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public int getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	public abstract int totalAmount(int beforeAmount);

	public VoucherEntity toEntity() {
		return new VoucherEntity(voucherId, amount, voucherType.toString());
	}
}
