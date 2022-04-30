package com.programmers.order.message;

public class LogMessage {
	public enum ErrorLogMessage {
		ERROR_PREFIX("error : {}"),
		IO_EXCEPTION("io checked Exception error"),
		NOT_FOUND_FILE("not found file resources ... "),
		NOT_CREATE_FILE("create file false ... "),
		NOT_FOUND_RESOURCE("not found resource ..."),
		NOT_EXECUTE_QUERY("execute query false ...");

		private final String message;

		ErrorLogMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "ErrorLogMessage{" +
					"message='" + message + '\'' +
					'}';
		}

		public static String getPrefix() {
			return ERROR_PREFIX.message;
		}

	}

	public enum InfoLogMessage {
		INFO_PREFIX("info : {}"),
		DUPLICATE_VOUCHER_REGISTER("쿠폰 등록을 재시도 하였습니다."),
		POSSIBLE_REGISTER("쿠폰 등록이 가능한 회원 입니다."),
		CUSTOMER_NOT_EXIST_EMAIL("회원이 존재하지 않습니다.");

		private final String message;

		InfoLogMessage(String message) {
			this.message = message;
		}

		public static String getPrefix() {
			return INFO_PREFIX.message;
		}

		@Override
		public String toString() {
			return "InfoLogMessage{" +
					"message='" + message + '\'' +
					'}';
		}
	}

}
