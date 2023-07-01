package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;

public final class VoucherMapper {

	private VoucherMapper() {
	}

	public static VoucherDTO toDTO(VoucherEntity voucherEntity) {
		Long voucherId = voucherEntity.getVoucherId();
		VoucherType voucherType = voucherEntity.getVoucherType();
		int amountValue = voucherEntity.getAmount();
		Amount amount = VoucherFactory.getVoucherAmount(amountValue, voucherType);
		return new VoucherDTO(voucherId, amount, voucherType);
	}

	public static VoucherEntity toVoucher(VoucherDTO voucherDTO) {
		Long voucherId = voucherDTO.getVoucherId();
		Amount amount = voucherDTO.getAmount();
		int amountValue = amount.getAmount();
		VoucherType voucherType = voucherDTO.getVoucherType();

		return new VoucherEntity(voucherId, amountValue, voucherType);
	}
}
