package com.programmers.order.io;

import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.Message;

@Component
public class Console implements Input, Output {

	private static final Scanner input = new Scanner(System.in);

	@Override
	public String read(Message message) {
		this.write(message);
		return input.nextLine();
	}

	@Override
	public void write(ErrorMessage message) {
		System.out.print(message.send());
	}

	@Override
	public void write(String message) {
		System.out.print(message);
	}

	@Override
	public void write(Message message) {
		System.out.println(message.send());
	}
}
