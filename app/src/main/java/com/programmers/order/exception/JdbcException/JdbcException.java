package com.programmers.order.exception.JdbcException;

public class JdbcException {

	public static class NotExecuteQueryException extends RuntimeException{
		public NotExecuteQueryException(String message) {
			super(message);
		}
	}
}
