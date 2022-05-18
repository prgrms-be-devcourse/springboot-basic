package com.prgrms.vouchermanagement.io;

import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class Input implements InputView{
	private final Scanner sc = new Scanner(System.in);

	@Override
	public long inputDetailsInfo() {
		try {
			String input = sc.nextLine();

			return Long.parseLong(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("VoucherDiscountInfo must be numeric", e);
		}
	}

	@Override
	public String inputVoucherType() {
		return sc.nextLine();
	}

	@Override
	public String inputSelectedMenu() {
		return sc.nextLine();
	}


}
