package com.example.voucher.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.voucher.domain.FixedAmountVoucher;
import com.example.voucher.domain.Voucher;

class MemoryVoucherRepositoryTest {
	VoucherRepository voucherRepository;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
	}

	@DisplayName("insert 메서드 동작확인")
	@Test
	void insert() {
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
		UUID expected = voucher.getVoucherId();

		UUID actual = voucherRepository.insert(voucher);

		assertEquals(expected, actual);

	}

	@DisplayName("findlAll 메서드 동작확인")
	@Test
	void findAll() {
		List<Voucher> vouchers = LongStream.range(1, 11)
			.mapToObj(v -> new FixedAmountVoucher(UUID.randomUUID(), v))
			.collect(Collectors.toList());
		int expectedSize = vouchers.size();

		for (Voucher voucher : vouchers) {
			voucherRepository.insert(voucher);
		}

		int actualSize = voucherRepository.findAll().size();
		assertEquals(expectedSize, actualSize);

	}

	@DisplayName("findlAll 수행시 값이 없을 때")
	@Test
	void findAll_but_not_data() {
		List<Voucher> vouchers = voucherRepository.findAll();

	}
}
