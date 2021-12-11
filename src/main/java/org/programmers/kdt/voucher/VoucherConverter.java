package org.programmers.kdt.voucher;

import org.programmers.kdt.voucher.factory.VoucherFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherConverter {
	public static List<VoucherDto> convertToVoucherDtoList(List<Voucher> vouchers) {
		return vouchers.stream().map(VoucherConverter::convertToVoucherDto).toList();
	}

	public static VoucherDto convertToVoucherDto(Voucher voucher) {
		return new VoucherDto(voucher.getVoucherType().toString(), voucher.getDiscount());
	}

	public static Voucher convertToVoucher(VoucherDetailDto voucherDetailDto) {
		return VoucherFactory.createVoucher(VoucherType.of(voucherDetailDto.getVoucherType()),
				UUID.fromString(voucherDetailDto.getVoucherId()),
				voucherDetailDto.getDiscountAmount());
	}

	public static VoucherDetailDto convertToVoucherDetailDto(Optional<Voucher> voucherOpt) {
		if (voucherOpt.isPresent()) {
			Voucher voucher = voucherOpt.get();
			return VoucherDetailDto.builder()
					.voucherId(voucher.getVoucherId().toString())
					.voucherType(voucher.getVoucherType().toString())
					.discountAmount(voucher.getDiscount())
					.status(voucher.getStatus().toString())
					.build();
		}
		return VoucherDetailDto.empty();
	}
}
