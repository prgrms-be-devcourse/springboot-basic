package org.prgrms.kdt.model.entity;

import org.prgrms.kdt.enums.VoucherType;

public class VoucherEntity {

	private Long voucherId;

	private int amount;

	private VoucherType voucherType;

	/**
	 * <p>json으로 deserialization하는 ObjectMapper 클래스에서 default 생성자를 요구하여 작성하였습니다.</p>
	 */
	public VoucherEntity() {
	}

	public VoucherEntity(Long voucherId, int amount, VoucherType voucherType) {
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
}
