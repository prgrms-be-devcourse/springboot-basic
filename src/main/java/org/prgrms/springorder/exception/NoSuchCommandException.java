package org.prgrms.springorder.exception;

public class NoSuchCommandException extends RuntimeException {

	public NoSuchCommandException(String message) {
		super(message);
	}
}
