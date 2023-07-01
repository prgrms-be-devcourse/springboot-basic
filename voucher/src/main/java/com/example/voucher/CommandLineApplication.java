package com.example.voucher;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Controller;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.io.Console;
import com.example.voucher.io.ModeType;
import com.example.voucher.service.VoucherService;

import jakarta.annotation.PostConstruct;

@Controller
public class CommandLineApplication {

	final private VoucherService voucherService;
	private boolean isOn = true;

	public CommandLineApplication(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostConstruct
	public void init() {
		run();
	}

	public void run() {
		while (isOn) {
			Console.printModeType();
			String readModeType = Console.readModeType();

			ModeType.getTypeMode(readModeType).ifPresentOrElse(
				(mode) -> processMode(mode),
				() -> Console.printError("Mode를 다시 선택해주세요")
			);
		}
	}

	public void processMode(ModeType mode) {
		switch (mode) {
			case Exit -> isOn = false;
			case Create -> createVoucher();
			case List -> getVouchers();
		}
	}

	public void createVoucher() {
		AtomicBoolean haveToCreate = new AtomicBoolean(true);

		while (haveToCreate.get()) {
			Console.printVoucherType();
			Integer inputVoucherType = Console.readVoucherType();
			VoucherType.getVouchersType(inputVoucherType).ifPresentOrElse(
				(voucherType) -> {
					processVoucherType(voucherType);
					haveToCreate.set(false);
				},
				() -> {
					Console.printError("VoucherType을 다시 선택해주세요");
				}
			);
		}
	}

	public Voucher processVoucherType(VoucherType voucherType) {
		Voucher voucher = switch (voucherType) {
			case FixedAmountDiscount -> {
				Console.printDiscountAmount();
				long discountAmount = Console.readDiscount();
				yield voucherService.createVoucher(voucherType, discountAmount);
			}
			case PercentDiscount -> {
				Console.printDiscountPercent();
				long discountPercent = Console.readDiscount();
				yield voucherService.createVoucher(voucherType, discountPercent);
			}
		};

		return voucher;
	}

	public void getVouchers() {
		List<Voucher> vouchers = voucherService.getVouchers();

		vouchers.stream()
			.map(o -> new VoucherDTO(o.getValue(), o.getVoucherType()))
			.forEach(Console::printVoucherInfo);
	}

}
