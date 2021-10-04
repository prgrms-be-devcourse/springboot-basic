package org.programmers.kdt.voucher;

import java.util.List;

public class VoucherConverter {
	public static List<VoucherDto> convertToVoucherDtoList(List<Voucher> vouchers) {
		return vouchers.stream().map(VoucherConverter::convertToVoucherDto).toList();
	}

	public static VoucherDto convertToVoucherDto(Voucher voucher) {
		return new VoucherDto(voucher.getVoucherType().toString(), voucher.getDiscount());
	}
}
