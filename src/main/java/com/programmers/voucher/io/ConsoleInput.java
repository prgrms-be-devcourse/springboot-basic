package com.programmers.voucher.io;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.EmptyBufferException;
import com.programmers.voucher.exception.ExceptionMessage;

public class ConsoleInput implements Input {

	private static final Logger log = LoggerFactory.getLogger(ConsoleInput.class);
	private final BufferedReader reader;

	public ConsoleInput(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public String read() {
		String input = "";
		try {
			input = reader.readLine();
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
		return input;
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			log.error(ExceptionMessage.EMPTY_BUFFER.getMessage());
			throw new EmptyBufferException();
		}
	}
}
