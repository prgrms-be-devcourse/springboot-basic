package com.example.voucher.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.enums.VoucherType;
import com.example.voucher.repository.MemoryVoucherRepository;
import com.example.voucher.repository.VoucherRepository;

class VoucherServiceTest {
	private VoucherService voucherService;
	private VoucherRepository voucherRepository;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
		voucherService = new VoucherService(voucherRepository);
	}

	@DisplayName("createVoucher 메서드 동작확인")
	@Test
	void createVoucher() {
		Voucher fixedAmountVoucher = voucherService.createVoucher(VoucherType.FixedAmount, 10);
		System.out.println(fixedAmountVoucher.getInfo());

		Voucher percentDiscountVoucher = voucherService.createVoucher(VoucherType.PercentDiscount, 10);
		System.out.println(percentDiscountVoucher.getInfo());
	}

	@DisplayName("getVouchers 메서드 동작확인")
	@Test
	void getVouchers() {
		Voucher fixedAmountVoucher = voucherService.createVoucher(VoucherType.FixedAmount, 10);
		Voucher percentDiscountVoucher = voucherService.createVoucher(VoucherType.PercentDiscount, 10);

		List<Voucher> vouchers = voucherService.getVouchers();

		int expeacted = 2;
		int actual = vouchers.size();
		assertEquals(expeacted, actual);
	}

}