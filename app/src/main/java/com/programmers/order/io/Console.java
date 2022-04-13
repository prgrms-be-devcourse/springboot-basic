package com.programmers.order.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Component;

import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;

@Component
public class Console implements Input, Output {

	/**
	 * worries: 여러개의 스레드로 사용해도 될런지...?
	 */
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	@Override
	public String read(BasicMessage message) throws IOException {
		this.write(message);
		return reader.readLine();
	}

	@Override
	public void write(BasicMessage message) throws IOException {
		writer.write(message.toString());
		writer.flush();
	}

	@Override
	public void write(ErrorMessage message) throws IOException {
		writer.write(message.toString());
		writer.flush();
	}
}
