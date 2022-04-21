package com.example.voucher.io;

import org.springframework.stereotype.Component;


@Component
public class ConsoleReader implements Input {

	@Override
	public String getCommand() {
		return null;
	}

	@Override
	public String getVoucherType() {
		return null;
	}

	@Override
	public int getDiscountAmount() {
		// TODO: Integer.parseInt() 변환 실패 시 로그 남기기
		return 0;
	}
}
