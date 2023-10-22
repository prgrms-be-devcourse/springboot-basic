package study.dev.spring.voucher.application.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.voucher.domain.Voucher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoucherMapper {

	public static VoucherInfo toVoucherInfo(final Voucher voucher) {
		return new VoucherInfo(
			voucher.getName(),
			voucher.getTypeDescription(),
			voucher.getDiscountAmount()
		);
	}
}
