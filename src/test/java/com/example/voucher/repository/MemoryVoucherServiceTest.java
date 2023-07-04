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

class MemoryVoucherServiceTest {
	VoucherRepository voucherRepository;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
	}

	@DisplayName("Voucher가 있을 때 save메서드가 성공하면 Voucher의 UUID를 반환한다")
	@Test
	void save() {
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
		UUID expected = voucher.getVoucherId();

		UUID actual = voucherRepository.save(voucher);

		assertEquals(expected, actual);
	}

	@DisplayName("빈 저장소에 특정 크기만큼 Voucher를 저장했을 때 findAll 메서드가 성공하면 해당 크기만큼 Voucher를 반환한다")
	@Test
	void findAll() {
		List<Voucher> vouchers = LongStream.range(1, 11)
			.mapToObj(v -> new FixedAmountVoucher(UUID.randomUUID(), v))
			.collect(Collectors.toList());
		int expectedSize = vouchers.size();

		for (Voucher voucher : vouchers) {
			voucherRepository.save(voucher);
		}

		int actualSize = voucherRepository.findAll().size();
		assertEquals(expectedSize, actualSize);
	}

	@DisplayName("빈 저장소에서 findlAll를 수행하면 iterator가 바라보는 다음값이 없다")
	@Test
	void findAll_but_not_data() {
		List<Voucher> vouchers = voucherRepository.findAll();

		boolean isEmpty = !vouchers.iterator().hasNext();
		assertTrue(isEmpty, "데이터가 없어야 합니다.");
	}

	@Test
	void func1(){

		List<Voucher> vouchers = voucherRepository.findAll();
		if(vouchers==null)
			System.out.println("true");

	}
}
