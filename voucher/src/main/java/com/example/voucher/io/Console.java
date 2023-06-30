package com.example.voucher.io;

import java.util.Scanner;

import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.domain.enums.VoucherType;

public class Console {

	private static Scanner scanner = new Scanner(System.in);

	private static final String APPLICATION_MODE_TYPE_MESSAGE = """
		=== Voucher Program ===
		Type exit to exit the program.
		Type create to create a new voucher.
		Type list to list all vouchers.
		""";
	private static final String VOUCHER_CREATE_TYPE_MESSAGE = """
		Input Number for select VoucherType
		1. FixedAmount
		2. PercentDiscount
    	""";
	private static final String DISCOUNT_PRICE_MESSAGE = """
 		input price for discount
 		""";
	private static final String DISCOUNT_PERCENT_MESSAGE = """
		input percent for discount
		""";

	private Console() {

	}

	public static void printModeType() {
		System.out.println(APPLICATION_MODE_TYPE_MESSAGE);
	}

	public static void printVoucherType() {
		System.out.println(VOUCHER_CREATE_TYPE_MESSAGE);
	}

	public static void printDiscountAmount() {
		System.out.println(DISCOUNT_PRICE_MESSAGE);
	}

	public static void printDiscountPercent() {
		System.out.println(DISCOUNT_PERCENT_MESSAGE);
	}

	public static void printVoucherInfo(VoucherDTO voucherDTO) {
		VoucherType voucherType = voucherDTO.voucherType();

		switch (voucherType) {
			case FixedAmountDiscount -> System.out.println(
				String.format("VoucherType : %s, discountAmount : %d", voucherDTO.voucherType(), voucherDTO.value()));
			case PercentDiscount -> System.out.println(
				String.format("VoucherType : %s, discountPercent : %d", voucherDTO.voucherType(), voucherDTO.value()));
		}
	}

	public static void printError(String errorMsg) {
		System.out.println(errorMsg);
	}

	public static String readModeType() {
		return scanner.nextLine();
	}

	public static Integer readVoucherType() {
		Integer type = scanner.nextInt();
		scanner.nextLine();

		return type;
	}

	public static long readDiscount() {
		Long discount = scanner.nextLong();
		scanner.nextLine();

		return discount;
	}

}
