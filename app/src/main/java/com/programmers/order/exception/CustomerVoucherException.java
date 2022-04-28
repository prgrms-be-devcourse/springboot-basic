package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class CustomerVoucherException {

	static public class DuplicateVoucherRegister extends RuntimeException {
		public DuplicateVoucherRegister(ErrorMessage message) {
			super(message.toString());
		}
	}
}
