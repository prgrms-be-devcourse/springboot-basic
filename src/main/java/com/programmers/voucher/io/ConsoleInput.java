package com.programmers.voucher.io;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleInput implements Input {

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

		}
		return input;
	}

	@Override
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {

		}
	}
}
