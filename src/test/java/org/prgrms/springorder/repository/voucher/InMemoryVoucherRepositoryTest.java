package org.prgrms.springorder.repository.voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;

class InMemoryVoucherRepositoryTest {

	private InMemoryVoucherRepository inMemoryVoucherRepository;

	@BeforeEach
	void init() {
		inMemoryVoucherRepository = new InMemoryVoucherRepository();
	}

	@Test
	@DisplayName("바우처를 저장하고, 저장된 바우처를 성공적으로 조회한다.")
	void test1() {
		//given
		UUID uuid = UUID.randomUUID();
		Voucher voucher = new FixedAmountVoucher(uuid, 1500);
		//when
		inMemoryVoucherRepository.save(voucher);
		Optional<Voucher> savedVoucher = inMemoryVoucherRepository.findById(uuid);
		//then
		Assertions.assertTrue(savedVoucher.isPresent());
		Assertions.assertEquals(voucher, savedVoucher.get());
	}

	@Test
	@DisplayName("바우처를 저장하고, 저장된 바우처리스트를 성공적으로 가져온다.")
	void test2() {
		//given
		int expectSize = 4;
		for (int i = 0; i < 4; i++) {
			if (i % 2 == 0) {
				inMemoryVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 50));
			} else {
				inMemoryVoucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 50));
			}
		}
		//when
		List<Voucher> savedVouchers = inMemoryVoucherRepository.findAll();
		//then
		Assertions.assertEquals(savedVouchers.size(), expectSize);

	}
}