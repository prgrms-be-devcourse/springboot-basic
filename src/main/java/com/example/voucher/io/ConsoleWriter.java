package com.example.voucher.io;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.voucher.exception.ErrorMessage.UNSUPPORTED_MESSAGE_TYPE;


@Component
public class ConsoleWriter<T> implements Output<T> {

	@Override
	public void printMessage(T message) {
		try {
			System.out.println(convert(message, message.getClass()));
		} catch (IllegalArgumentException e) {
			// TODO: 로그 남기기
			System.out.println(e.getMessage());
		}
	}

	private String convert(T message, Class<?> classType) {
		if (classType == String.class) {
			return message.toString();
		}
		if (classType == List.class) {
			return message.toString();
		}
		throw new IllegalArgumentException(UNSUPPORTED_MESSAGE_TYPE.name());
	}
}
