package study.dev.spring.voucher.fixture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoucherFixture {

	public static Voucher getFixedVoucher() {
		return Voucher.createVoucher(VoucherType.FIXED, "voucher", 1000);
	}

	public static CreateVoucherRequest getCreateRequest() {
		return new CreateVoucherRequest("voucher", "FIXED", 1000);
	}

	public static VoucherInfo getVoucherInfo() {
		return new VoucherInfo("voucher", "정액 할인", 1000);
	}
}
