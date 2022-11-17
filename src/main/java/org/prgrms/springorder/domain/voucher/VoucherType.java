package org.prgrms.springorder.domain.voucher;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.util.Arrays;
import java.util.Objects;

import org.prgrms.springorder.domain.Message;
import org.prgrms.springorder.exception.NoSuchVoucherException;

public enum VoucherType {

	FIXED_AMOUNT("1",
		"fixedAmount",
		Message.FIXED_AMOUNT_MESSAGE),
	PERCENT_DISCOUNT("2",
		"percentDiscount",
		Message.PERCENT_DISCOUNT_MESSAGE);

	private final String order;
	private final String name;
	private final Message message;

	VoucherType(String order, String name, Message message) {
		this.order = order;
		this.name = name;
		this.message = message;
	}

	public static VoucherType getVoucherByOrder(String order) {
		return Arrays.stream(values())
			.filter(a -> Objects.equals(a.order, order))
			.findAny()
			.orElseThrow(() -> new NoSuchVoucherException(NO_SUCH_VOUCHER_MESSAGE));
	}

	public static VoucherType getVoucherByName(String name) {
		return Arrays.stream(values())
			.filter(a -> Objects.equals(a.name, name))
			.findAny()
			.orElseThrow(() -> new NoSuchVoucherException(NO_SUCH_VOUCHER_MESSAGE));
	}

	public Message getMessage() {
		return message;
	}

}
