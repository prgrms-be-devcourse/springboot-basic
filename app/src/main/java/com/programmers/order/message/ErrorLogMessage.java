package com.programmers.order.message;

public enum ErrorLogMessage {
	IO_EXCEPTION("io checked Exception error"),
	NOT_FOUND_FILE("not found file resources ... "),
	NOT_CREATE_FILE("create file false ... ");

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
		return "error : {}";
	}
}
