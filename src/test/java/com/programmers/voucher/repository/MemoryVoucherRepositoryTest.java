package com.programmers.voucher.repository;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.voucher.config.AppConfig;
import com.programmers.voucher.domain.FixedDiscountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;

class MemoryVoucherRepositoryTest {

	AppConfig appConfig = new AppConfig();
	VoucherRepository repository = appConfig.voucherRepository();

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

		repository.save(voucher1);
		repository.save(voucher2);
		List<Voucher> vouchers = repository.findAll();

		Assertions.assertThat(vouchers.size()).isEqualTo(2);
	}
}