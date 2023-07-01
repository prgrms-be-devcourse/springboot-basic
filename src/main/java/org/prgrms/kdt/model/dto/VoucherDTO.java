package org.prgrms.kdt.model.dto;

import java.util.Objects;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;

public class VoucherDTO {

	private final Long voucherId;

	private final Amount amount;

	private final VoucherType voucherType;

	public VoucherDTO(Long voucherId, Amount amount, VoucherType voucherType) {
		this.voucherId = voucherId;
		this.amount = amount;
		this.voucherType = voucherType;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public Amount getAmount() {
		return amount;
	}

	public VoucherType getVoucherType() {
		return voucherType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		VoucherDTO that = (VoucherDTO)o;

		if (!Objects.equals(voucherId, that.voucherId))
			return false;
		if (!amount.equals(that.amount))
			return false;
		return voucherType == that.voucherType;
	}

	@Override
	public int hashCode() {
		int result = voucherId != null ? voucherId.hashCode() : 0;
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (voucherType != null ? voucherType.hashCode() : 0);
		return result;
	}
}
