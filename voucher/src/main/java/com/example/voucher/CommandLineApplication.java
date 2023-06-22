package com.example.voucher;

import java.util.List;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.io.Console;
import com.example.voucher.io.ModeType;
import com.example.voucher.service.VoucherService;

public class CommandLineApplication {
	private VoucherService voucherService;
	private boolean isOn = true;

	public CommandLineApplication(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public void run() {

		while (isOn) {
			Console.printModeType();
			ModeType mode = ModeType.getTypeMode(Console.readModeType());

			switch (mode) {

				case Exit:
					isOn = false;
					break;

				case Create:
					createVoucher();
					break;

				case List:
					getVouchers();
					break;

				case Null:
					Console.printError("Mode를 다시 선택해주세요");
					break;

			}
		}
	}

	public void createVoucher() {
		boolean isSatisfied = false;
		while (!isSatisfied) {
			Console.printVoucherType();
			VoucherType voucherType = VoucherType.getVouchersType(Console.readVoucherType());

			Voucher voucher = null;
			switch (voucherType) {

				case FixedAmount:
					Console.printDiscountAmount();
					voucher = voucherService.createVoucher(voucherType, Console.readDiscount());
					Console.printVoucherInfo(voucher.getInfo());
					isSatisfied = true;
					break;

				case PercentDiscount:
					Console.printDiscountPercent();
					voucher = voucherService.createVoucher(voucherType, Console.readDiscount());
					Console.printVoucherInfo(voucher.getInfo());
					isSatisfied = true;
					break;

				case Null:
					Console.printError("VoucherType을 다시 선택해주세요");
					break;
			}
		}
	}

	public void getVouchers() {
		List<Voucher> vouchers = voucherService.getVouchers();
		vouchers.stream().forEach(v -> Console.printVoucherInfo(v.getInfo()));
	}

}
