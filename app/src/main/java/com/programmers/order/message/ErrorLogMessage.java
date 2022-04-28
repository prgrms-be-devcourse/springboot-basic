package com.programmers.order.message;

public enum ErrorLogMessage {
	ERROR_PREFIX("error : {}"),
	INFO_PREFIX("info : {}"),
	IO_EXCEPTION("io checked Exception error"),
	NOT_FOUND_FILE("not found file resources ... "),
	NOT_CREATE_FILE("create file false ... "),
	NOT_FOUND_RESOURCE("not found resource ..."),
	GRATER_THAN_1_SIZE("expect 1 size but grater than 2...."),
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

	public static String getInfoLogPrefix() {
		return INFO_PREFIX.message;
	}
}
