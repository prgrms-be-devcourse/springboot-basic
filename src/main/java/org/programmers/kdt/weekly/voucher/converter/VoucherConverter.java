package org.programmers.kdt.weekly.voucher.converter;

import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto.VoucherResponse;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.springframework.stereotype.Component;

@Component
public class VoucherConverter {

	public VoucherResponse convertVoucherResponse(Voucher voucher) {
		return new VoucherResponse(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getValue(),
			voucher.getCreatedAt());
	}

	public Voucher convertVoucher(VoucherResponse voucherResponse) {

		return voucherResponse.type()
			.create(voucherResponse.id(), voucherResponse.value(), voucherResponse.createdAt());
	}
}
