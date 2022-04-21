package com.example.voucher.io;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;


@Component
public class ConsoleReader implements Input {

	private final Scanner sc = new Scanner(System.in);

	@Override
	public String getCommand() {
		return sc.nextLine();
	}

	@Override
	public String getVoucherType() {
		return sc.nextLine();
	}

	@Override
	public int getDiscountAmount() {
		// TODO: Integer.parseInt() 변환 실패 시 로그 남기기
		int discountAmount = 0;
		try {
			discountAmount = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_INPUT.name());
		}
		return discountAmount;
	}
}
