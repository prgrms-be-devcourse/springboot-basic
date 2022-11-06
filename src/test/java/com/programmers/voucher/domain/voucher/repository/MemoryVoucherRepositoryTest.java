package com.programmers.voucher.domain.voucher.repository;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.FixedDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.model.Voucher;

class MemoryVoucherRepositoryTest {

	VoucherRepository repository = new MemoryVoucherRepository();

	@BeforeEach
	public void beforeEach() {
		repository.clear();
	}

	@Test
	@DisplayName("바우처를 저장하고 조회하면 성공적으로 저장, 조회된다.")
	void save() {
		UUID voucherId = UUID.randomUUID();
		Voucher fixedVoucher = new FixedDiscountVoucher(voucherId, 1000);

		repository.save(fixedVoucher);
		Voucher findVoucher = repository.findByUUID(voucherId);

		Assertions.assertThat(fixedVoucher).isEqualTo(findVoucher);
	}

	@Test
	@DisplayName("바우처 리스트를 조회하면 성공적으로 조회된다.")
	void findAllVoucher() {
		Voucher voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
		Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 20);

		List<Voucher> beforeSave = repository.findAll();
		int beforeSize = beforeSave.size();
		repository.save(voucher1);
		repository.save(voucher2);
		int afterSize = repository.findAll().size();

		Assertions.assertThat(afterSize).isEqualTo(beforeSize + 2);
	}
}