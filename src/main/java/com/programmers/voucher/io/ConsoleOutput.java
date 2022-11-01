package com.programmers.voucher.io;

import java.io.BufferedWriter;
import java.io.IOException;

public class ConsoleOutput implements Output {

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
		}
	}

	@Override
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
		}
	}
}
