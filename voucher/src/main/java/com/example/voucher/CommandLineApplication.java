package com.example.voucher;

import java.util.List;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.io.Console;
import com.example.voucher.io.ModeType;
import com.example.voucher.service.VoucherService;

public class CommandLineApplication {

	final private VoucherService voucherService;
	private boolean isOn = true;

	public CommandLineApplication(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public void run() {
		while (isOn) {
			Console.printModeType();
			ModeType mode = ModeType.getTypeMode(Console.readModeType());
			processMode(mode);
		}
	}

	public void processMode(ModeType mode) {
		switch (mode) {
			case Exit -> isOn = false;
			case Create -> createVoucher();
			case List -> getVouchers();
			case Null -> Console.printError("Mode를 다시 선택해주세요");
		}
	}

	public void createVoucher() {
		Console.printVoucherType();
		VoucherType voucherType = VoucherType.getVouchersType(Console.readVoucherType());
		Voucher voucher = processVoucherType(voucherType);

		while (voucher == null) {
			Console.printError("VoucherType을 다시 선택해주세요");
			Console.printVoucherType();
			voucherType = VoucherType.getVouchersType(Console.readVoucherType());
			voucher = processVoucherType(voucherType);
		}
	}

	public Voucher processVoucherType(VoucherType voucherType) {
		Voucher voucher = switch (voucherType) {
			case FixedAmount -> {
				Console.printDiscountAmount();
				yield voucherService.createVoucher(voucherType, Console.readDiscount());
			}
			case PercentDiscount -> {
				Console.printDiscountPercent();
				yield voucherService.createVoucher(voucherType, Console.readDiscount());
			}
			case Null -> null;
		};
		return voucher;
	}

	public void getVouchers() {
		List<Voucher> vouchers = voucherService.getVouchers();
		vouchers.stream().forEach(v -> Console.printVoucherInfo(v.getInfo()));
	}

}
