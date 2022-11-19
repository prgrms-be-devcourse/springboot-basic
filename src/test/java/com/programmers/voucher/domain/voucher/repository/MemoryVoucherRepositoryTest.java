package com.programmers.voucher.domain.voucher.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;

class MemoryVoucherRepositoryTest {

	VoucherRepository repository = new MemoryVoucherRepository();
	VoucherFactory factory = new VoucherFactory();

	@BeforeEach
	public void beforeEach() {
		repository.clear();
	}

	@Test
	@DisplayName("바우처를 저장하고 조회하면 성공적으로 저장, 조회된다.")
	void save() {
		Voucher voucher = factory.createVoucher(VoucherType.FIXED, "1000");

		repository.save(voucher);
		Voucher findVoucher = repository.findById(voucher.getVoucherId());

		Assertions.assertThat(findVoucher).isEqualTo(voucher);
	}

	@Test
	@DisplayName("바우처 리스트를 조회하면 성공적으로 조회된다.")
	void findAllVoucher() {
		Voucher voucher1 = factory.createVoucher(VoucherType.FIXED, "1000");
		Voucher voucher2 = factory.createVoucher(VoucherType.PERCENT, "20");

		List<Voucher> beforeSave = repository.findAll();
		int beforeSize = beforeSave.size();
		repository.save(voucher1);
		repository.save(voucher2);
		int afterSize = repository.findAll().size();

		Assertions.assertThat(afterSize).isEqualTo(beforeSize + 2);
	}
}