package org.prgrms.springorder.domain;

import java.util.Arrays;
import java.util.UUID;

import org.prgrms.springorder.exception.NoSuchVoucherException;

public enum VoucherType {

	FIXED_AMOUNT("1",
		"fixedAmount",
		Message.FIXED_AMOUNT_MESSAGE,
		FixedAmountVoucher::new),
	PERCENT_DISCOUNT("2",
		"percentDiscount",
		Message.PERCENT_DISCOUNT_MESSAGE,
		PercentDiscountVoucher::new);

	private final String order;
	private final String name;
	private final Message message;
	private final VoucherFunction<Voucher, UUID, Long> concreteVoucher;

	VoucherType(String order, String name, Message message, VoucherFunction<Voucher, UUID, Long> concreteVoucher) {
		this.order = order;
		this.name = name;
		this.message = message;
		this.concreteVoucher = concreteVoucher;
	}

	public static VoucherType getVoucherByOrder(String order) {
		return Arrays.stream(values())
			.filter(a -> a.order.equals(order))
			.findAny()
			.orElseThrow(() -> new NoSuchVoucherException("No Such Voucher"));
	}

	public static VoucherType getVoucherByName(String name) {
		return Arrays.stream(values())
			.filter(a -> a.name.equals(name))
			.findAny()
			.orElseThrow(() -> new NoSuchVoucherException("No Such Voucher"));
	}

	public Message getMessage() {
		return message;
	}

	public Voucher voucherMaker(UUID uuid, long value) {
		return concreteVoucher.apply(uuid, value);
	}

}
