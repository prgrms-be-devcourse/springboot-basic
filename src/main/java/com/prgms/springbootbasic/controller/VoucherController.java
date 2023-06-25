package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.model.VouchersInMemory;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.Menu;
import com.prgms.springbootbasic.util.VoucherType;

import java.util.List;
import java.util.stream.Collectors;

public class VoucherController {
	
	private final Console console;
	
	public VoucherController(Console console) {
		this.console = console;
	}
	
	public boolean run() {
		try {
			String command = console.init();
			Menu menu = Menu.of(command);
			VouchersInMemory vouchersInMemory = new VouchersInMemory();
			if (menu == Menu.EXIT) {
				exit();
				return false;
			}
			init(menu, vouchersInMemory);
		} catch (Exception e) {
			console.showExceptionMessage(e.getMessage());
		}
		return true;
	}
	
	private void init(Menu menu, VouchersInMemory vouchersInMemory) {
		if (menu == Menu.CREATE) {
			Voucher voucher = create();
			vouchersInMemory.save(voucher);
		} else if (menu == Menu.LIST) {
			List<Voucher> vouchers = vouchersInMemory.findAll();
			list(vouchers);
		}
	}
	
	private void exit() {
		console.exit();
	}
	
	private Voucher create() {
		VoucherType voucherType;
		while((voucherType = inputVoucherType()) == null);
		Voucher voucher;
		while((voucher = inputVoucherNumber(voucherType)) == null);
		return voucher;
	}
	
	private VoucherType inputVoucherType() {
		VoucherType voucherType = null;
		try {
			String voucherTypeOfString = console.inputVoucherType();
			voucherType = VoucherType.of(voucherTypeOfString);
		} catch (Exception e) {
			console.showExceptionMessage(e.getMessage());
		}
		return voucherType;
	}
	
	private Voucher inputVoucherNumber(VoucherType voucherType) {
		Voucher voucher = null;
		try {
			Long number = console.inputVoucherNumber();
			voucher = voucherType.createVoucher(number);
		} catch (Exception e) {
			console.showExceptionMessage(e.getMessage());
		}
		return voucher;
	}
	
	private void list(List<Voucher> vouchers) {
		List<String> vouchersOfString = vouchers.stream()
				                        .map(Voucher::toString)
				                        .collect(Collectors.toList());
		console.showVoucherList(vouchersOfString);
	}
	
}
