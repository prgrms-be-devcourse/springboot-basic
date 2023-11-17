package study.dev.spring.voucher.fixture;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VoucherFixture {

	public static Voucher getFixedVoucher() {
		return Voucher.of(VoucherType.FIXED, "Fixed_Voucher", 1000);
	}

	public static Voucher getFixedVoucher(String voucherId) {
		return new Voucher(voucherId, "Fixed_Voucher", VoucherType.FIXED, 1000);
	}

	public static List<Voucher> getVouchers() {
		return List.of(VoucherFixture.getFixedVoucher(), VoucherFixture.getFixedVoucher());
	}

	public static Voucher getPercentVoucher() {
		return Voucher.of(VoucherType.PERCENT, "Percent_Voucher", 10);
	}

	public static CreateVoucherRequest getCreateRequest() {
		return new CreateVoucherRequest("voucher", "FIXED", 1000);
	}

	public static VoucherInfo getVoucherInfo() {
		return new VoucherInfo("uuid", "voucher", "정액 할인", 1000);
	}
}
