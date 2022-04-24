package org.prgms.springbootbasic.voucher.vo;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * VoucherType -> Voucher의 종류
 */
public enum VoucherType {
	/* 고정 금액 Voucher*/
	FIXEDAMOUNTVOUCHER {
		@Override
		public Voucher createVoucher(int param) {
			return new FixedAmountVoucher(param);
		}
	},
	/* 할인율 적용 Voucher*/
	PERCENTDISCOUNTVOUCHER {
		@Override
		public Voucher createVoucher(int param) {
			return new PercentDiscountVoucher((int)param);
		}
	};

	/**
	 * 원하는 Voucher의 타입을 입력받고 VoucherType을 만환하는 메서
	 *
	 * @param voucherType
	 * @return VoucherType : 입력한 VoucherType에 맞는
	 * @throws IllegalArgumentException 잘못된 type을 입력했다면 에러 발
	 */
	public static VoucherType of(String voucherType) {
		return Arrays.stream(VoucherType.values())
			.filter(type -> type.name().equals(voucherType))
			.findAny()
			.orElseThrow(() -> new IllegalArgumentException(
				MessageFormat.format("voucherType을 잘못 입력하셨습니다 entered voucherType : {0}", voucherType)));
	}

	/**
	 * VoucherType에 해당하는 Voucher를 생성하는 메서드
	 *
	 * @param param
	 * @return Voucher
	 */
	public abstract Voucher createVoucher(int param);

}
