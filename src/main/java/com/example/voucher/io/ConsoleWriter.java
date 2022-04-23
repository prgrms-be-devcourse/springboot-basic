package com.example.voucher.io;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter implements Output {

	@Override
	public void printMessage(String message) {
		System.out.println(message);
	}
}
