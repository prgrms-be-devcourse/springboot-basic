package com.programmers.order.exception;

import com.programmers.order.message.ErrorMessage;

public class JdbcException {

	public static class NotExecuteQuery extends RuntimeException {
		public NotExecuteQuery(ErrorMessage message) {
			super(message.toString());
		}
	}
}
