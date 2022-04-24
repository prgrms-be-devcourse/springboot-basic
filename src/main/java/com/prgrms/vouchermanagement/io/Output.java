package com.prgrms.vouchermanagement.io;

import java.util.List;

import org.springframework.stereotype.Component;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Component
public class Output implements OutputView {
	@Override
	public void showMenu() {
		System.out.println(ConsoleCommand.SHOW_MENU_OPTION);
	}

	@Override
	public void printVoucher(Voucher target) {
		System.out.println(target);
	}

	@Override
	public void showVoucherMenu() {
		System.out.println(ConsoleCommand.SHOW_VOUCHER_TYPE_OPTION);
	}

	@Override
	public void requestVoucherInfo() {
		System.out.println(ConsoleCommand.REQUEST_VOUCHER_INFO);
	}

	@Override
	public <T> void showAll(List<T> list) {
		if (list.isEmpty())
			System.out.println(ConsoleCommand.EMPTY);

		list.stream().forEach(System.out::println);
	}

	@Override
	public void printErrorMessage(String message) {
		System.out.println(message);
	}
}
