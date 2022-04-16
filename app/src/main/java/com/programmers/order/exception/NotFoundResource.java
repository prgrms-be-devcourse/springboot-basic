package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class NotFoundResource extends RuntimeException {
	public NotFoundResource(ErrorMessage message) {
		super(message.toString());
	}
}
