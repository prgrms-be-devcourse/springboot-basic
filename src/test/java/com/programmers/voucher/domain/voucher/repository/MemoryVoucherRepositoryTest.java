package com.programmers.voucher.domain.voucher.repository;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;

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
		Voucher fixedVoucher = new Voucher(voucherId, VoucherType.FIXED, "1000");

		repository.save(fixedVoucher);
		Voucher findVoucher = repository.findByUUID(voucherId);

		Assertions.assertThat(findVoucher).isEqualTo(fixedVoucher);
	}

	@Test
	@DisplayName("바우처 리스트를 조회하면 성공적으로 조회된다.")
	void findAllVoucher() {
		Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED, "1000");
		Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.PERCENT, "20");

		List<Voucher> beforeSave = repository.findAll();
		int beforeSize = beforeSave.size();
		repository.save(voucher1);
		repository.save(voucher2);
		int afterSize = repository.findAll().size();

		Assertions.assertThat(afterSize).isEqualTo(beforeSize + 2);
	}
}