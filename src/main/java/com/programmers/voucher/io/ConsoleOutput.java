package com.programmers.voucher.io;

import java.io.BufferedWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.programmers.voucher.exception.ExceptionMessage;

public class ConsoleOutput implements Output {

	private static final Logger log = LoggerFactory.getLogger(ConsoleOutput.class);
	private final BufferedWriter writer;

	public ConsoleOutput(BufferedWriter writer) {
		this.writer = writer;
	}

	@Override
	public void write(String str) {
		try {
			writer.write(str + System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			log.error(ExceptionMessage.IO.getMessage());
			throw new RuntimeException(ExceptionMessage.IO.getMessage());
		}
	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			log.error(ExceptionMessage.IO.getMessage());
			throw new RuntimeException(ExceptionMessage.IO.getMessage());
		}
	}
}
