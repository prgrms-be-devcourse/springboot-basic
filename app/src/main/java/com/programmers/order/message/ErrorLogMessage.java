package com.programmers.order.message;

public enum ErrorLogMessage {
	LOG_PREFIX("error : {}"),
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

	public static String getLogPrefix() {
		return LOG_PREFIX.message;
	}
}
