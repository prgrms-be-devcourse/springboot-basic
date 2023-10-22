package study.dev.spring.voucher.infrastructure.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoucherMapper {

	public static Voucher toVoucher(final VoucherData voucherData) {
		return new Voucher(
			voucherData.getUuid(),
			voucherData.getName(),
			VoucherType.valueOf(voucherData.getVoucherType()),
			voucherData.getDiscountAmount()
		);
	}

	public static VoucherData toVoucherData(final Voucher voucher) {
		return new VoucherData(
			voucher.getUuid(),
			voucher.getName(),
			voucher.getTypeName(),
			voucher.getDiscountAmount()
		);
	}
}
