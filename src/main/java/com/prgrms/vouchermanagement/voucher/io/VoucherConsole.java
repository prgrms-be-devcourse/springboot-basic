package com.prgrms.vouchermanagement.voucher.io;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.prgrms.vouchermanagement.voucher.domain.Voucher;

@Component
public class VoucherConsole implements VoucherInput, VoucherOutput {

	private final Scanner sc = new Scanner(System.in);
	private final Logger logger = LoggerFactory.getLogger(VoucherConsole.class);

	@Override
	public void showMenu() {
		System.out.println("==== Voucher Program ====");
		System.out.println("Type exit to exit program.");
		System.out.println("Type create to create a new voucher.");
		System.out.println("Type list to list all vouchers.");
	}

	@Override
	public long inputVoucherInfo() {
		try {
			String input = sc.nextLine();

			return Long.parseLong(input);
		} catch (NumberFormatException e) {
			failCreation();

			throw new IllegalArgumentException("VoucherDiscountInfo must be numeric", e);
		}
	}

	@Override
	public String selectVoucherType() {
		return sc.nextLine();
	}

	@Override
	public String selectedMenu() {
		return sc.nextLine();
	}

	@Override
	public void notifyNoMappingSelection() {
		System.out.println("일치하는 항목이 존재하지 않습니다");
	}

	@Override
	public <T> void showAll(List<T> list) {
		if (list.isEmpty())
			System.out.println("비어있습니다");

		list.stream().forEach(System.out::println);
	}

	@Override
	public void showVoucherInfo(Voucher voucher) {
		System.out.println(MessageFormat.format("Created new Voucher ", voucher));
	}

	@Override
	public void failCreation() {
		System.out.println("생성에 실패하였습니다");
	}

	@Override
	public void showVoucherMenu() {
		System.out.println("==== Please type Voucher Type ====");
		System.out.println("Type fixed to create a fixed amount voucher.");
		System.out.println("Type percent to create a percent discount voucher");
	}

	@Override
	public void requestVoucherInfo() {
		System.out.println("==== Please type Voucher Info ====");
		System.out.println("Type discount amount(or percent)");
	}
}
