package org.prgrms.kdt.model.dto;

import org.prgrms.kdt.model.entity.VoucherEntity;

public record VoucherResponse(Long voucherId, int amount, String voucherType) {

	public static VoucherResponse from(VoucherEntity voucherEntity) {
		return new VoucherResponse(voucherEntity.getVoucherId(), voucherEntity.getAmount(), voucherEntity.getVoucherType());
	}
}
