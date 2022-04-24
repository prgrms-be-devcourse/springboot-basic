package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.UUID;

public class VoucherInfo {
	private final UUID voucherId;
	private final VoucherType type;
	private final long value;

	private VoucherInfo(UUID voucherId, VoucherType type, long value) {
		this.voucherId = voucherId;
		this.type = type;
		this.value = value;
	}

	public static VoucherInfo of(UUID voucherId, VoucherType type, long value) {
		return new VoucherInfo(voucherId, type, value);
	}



	public static Voucher infoToVoucher(String voucherInfo){
		String[] info = voucherInfo.trim().split(" ");

		checkArgument(Arrays
			.stream(VoucherType.values()).anyMatch(type ->
				type.name().equals(info[1])
			), "일차하는 VoucherType이 없습니다.");

		if(info[1].equals(VoucherType.FIXEDAMOUNTVOUCHER.name())){
			return new FixedAmountVoucher(UUID.fromString(info[0]), Long.valueOf(info[2]));
		}else {
			return new PercentDiscountVoucher(UUID.fromString(info[0]), Integer.valueOf(info[2]));
		}
	}

	@Override
	public String toString() {
		return voucherId + " " + type.name() + " " + value;
	}
}
