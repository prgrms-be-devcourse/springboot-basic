package com.example.voucher.io;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.voucher.exception.ErrorMessage.UNSUPPORTED_MESSAGE_TYPE;


@Component
public class ConsoleWriter<T> implements Output<T> {

	@Override
	public void printMessage(T message) {
		try {
			System.out.println(convert(message));
		} catch (IllegalArgumentException e) {
			// TODO: 로그 남기기
			System.out.println(e.getMessage());
		}
	}

	private String convert(T message) {
		if (message instanceof String) {
			return message.toString();
		}
		if (message instanceof List) {
			return message.toString();
		}
		throw new IllegalArgumentException(UNSUPPORTED_MESSAGE_TYPE.getMessage());
	}
}
