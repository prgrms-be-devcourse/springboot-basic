package org.prgrms.kdt.model.dto;

import java.util.Objects;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.entity.VoucherEntity;

public final class VoucherDTO {
	private final Long voucherId;
	private final Amount amount;
	private final VoucherType voucherType;

	public VoucherDTO(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long voucherId() {
		return voucherId;
	}

	public Amount amount() {
		return amount;
	}

	public VoucherType voucherType() {
		return voucherType;
	}

	public VoucherResponse toResponse() {
		int amountValue =amount().getAmount();
		String voucherType = voucherType().toString();
		return new VoucherResponse(voucherId, amountValue, voucherType);
	}

	public VoucherEntity toEntity() {
		return new VoucherEntity(voucherId, amount().getAmount(), voucherType);
	}

}
