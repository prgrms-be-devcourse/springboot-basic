package com.example.voucher.io;

import java.util.Scanner;

import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.domain.enums.VoucherType;

public class Console {

	private static Scanner scanner = new Scanner(System.in);

	private Console() {

	}

	public static void printModeType() {
		System.out.println();
		System.out.println("=== Voucher Program ===");
		System.out.println("Type exit to exit the program.");
		System.out.println("Type create to create a new voucher.");
		System.out.println("Type list to list all vouchers.");
	}

	public static void printVoucherType() {
		System.out.println();
		System.out.println("Input Number for select VoucherType");
		System.out.println("1. FixedAmount");
		System.out.println("2. PercentDiscount");
	}

	public static void printDiscountAmount() {
		System.out.println();
		System.out.println("input price for discount");
	}

	public static void printDiscountPercent() {
		System.out.println();
		System.out.println("input percent for discount");
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
		System.out.println();
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
