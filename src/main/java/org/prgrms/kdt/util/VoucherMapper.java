package org.prgrms.kdt.util;

import java.util.ArrayList;
import java.util.List;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.Amount;
import org.prgrms.kdt.model.entity.VoucherEntity;

public class VoucherMapper {
	public static VoucherDTO entityToDTO(VoucherEntity voucherEntity) {
		Long voucherId = voucherEntity.getVoucherId();
		Amount amount = voucherEntity.getAmount();
		VoucherType voucherType = voucherEntity.getVoucherType();

		return new VoucherDTO(voucherId, amount, voucherType);
	}

	public static VoucherEntity dtoToEntity(VoucherDTO voucherDTO) {
		Long voucherId = voucherDTO.getVoucherId();
		Amount amount = voucherDTO.getAmount();
		VoucherType voucherType = voucherDTO.getVoucherType();

		return new VoucherEntity(voucherId, amount, voucherType);
	}
}
