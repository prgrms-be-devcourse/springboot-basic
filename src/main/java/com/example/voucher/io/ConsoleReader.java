package com.example.voucher.io;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;


@Component
public class ConsoleReader implements Input {

	private final Scanner sc = new Scanner(System.in);

	@Override
	public String getString() {
		return sc.nextLine();
	}

	@Override
	public int getInt() {
		// TODO: Integer.parseInt() 변환 실패 시 로그 남기기
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_INPUT.name());
		}
	}
}
