package com.example.voucher.io;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter<T> implements Output<T> {

	@Override
	public void printMessage(T message) {

	}
}
