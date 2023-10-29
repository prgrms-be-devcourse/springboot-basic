package com.programmers.springbasic.repository.voucher;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.programmers.springbasic.entity.voucher.FixedAmountVoucher;
import com.programmers.springbasic.entity.voucher.PercentDiscountVoucher;
import com.programmers.springbasic.entity.voucher.Voucher;

class MemoryVoucherRepositoryTest {

	private MemoryVoucherRepository voucherRepository;
	private Voucher voucher1;
	private Voucher voucher2;
	private UUID voucherId1;
	private UUID voucherId2;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
		voucherId1 = UUID.randomUUID();
		voucherId2 = UUID.randomUUID();
		voucher1 = new FixedAmountVoucher(voucherId1, 1000);
		voucher2 = new PercentDiscountVoucher(voucherId2, 20);
	}

	@Test
	void 바우처를_저장한다() {
		Voucher savedVoucher = voucherRepository.insert(voucher1);

		assertThat(savedVoucher, is(equalTo(voucher1)));
	}

	@Test
	void 바우처_세부사항을_변경한다() {
		voucherRepository.insert(voucher1);
		Voucher updatedVoucher = new FixedAmountVoucher(voucherId1, 2000);

		voucherRepository.update(updatedVoucher);

		assertThat(voucherRepository.findById(voucherId1).get(), is(equalTo(updatedVoucher)));
	}

	@Test
	void 모든_바우처를_조회한다() {
		voucherRepository.insert(voucher1);
		voucherRepository.insert(voucher2);

		List<Voucher> vouchers = voucherRepository.findAll();

		assertThat(vouchers, containsInAnyOrder(voucher1, voucher2));
	}

	@Test
	void 아이디로_바우처를_조회한다() {
		voucherRepository.insert(voucher1);

		Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId1);

		assertThat(foundVoucher.isPresent(), is(true));
		assertThat(foundVoucher.get(), is(equalTo(voucher1)));
	}

	@Test
	void 바우처를_삭제한다() {
		voucherRepository.insert(voucher1);

		voucherRepository.deleteById(voucherId1);

		assertThat(voucherRepository.findById(voucherId1).isPresent(), is(false));
	}

	@Test
	void 아이디_여러개로_바우처_목록을_조회한다() {
		voucherRepository.insert(voucher1);
		voucherRepository.insert(voucher2);
		List<UUID> ids = Arrays.asList(voucherId1, voucherId2);

		List<Voucher> foundVouchers = voucherRepository.findAllById(ids);

		assertThat(foundVouchers, containsInAnyOrder(voucher1, voucher2));
	}
}
