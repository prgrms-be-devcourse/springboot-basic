package org.programmers.kdt.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.Voucher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherRepositoryTest {
	List<Voucher> list = new ArrayList<>();
	VoucherRepository voucherRepository = new FileVoucherRepository("TestVoucherData.txt");

	@AfterAll
	void tearDown() {
		for (Voucher voucher : list) {
			voucherRepository.deleteVoucher(voucher);
		}
	}

	@Test
	@Order(1)
	@DisplayName("바우처 추가하기")
	void insert() {
		Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
		Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), 2000);
		Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 3000);
		list.add(voucher);
		list.add(voucher2);
		list.add(voucher3);

		assertThat(voucherRepository.insert(voucher)).isEqualTo(voucher);
		assertThat(voucherRepository.insert(voucher2)).isEqualTo(voucher2);
		assertThat(voucherRepository.insert(voucher3)).isEqualTo(voucher3);
	}

	@Test
	@Order(2)
	@DisplayName("ID로 바우처 찾기")
	void findById() {
		Voucher voucher = list.get(0);
		Voucher voucher2 = list.get(1);

		assertThat(voucherRepository.findById(voucher.getVoucherId())).isEqualTo(Optional.of(voucher));
		assertThat(voucherRepository.findById(voucher.getVoucherId())).isNotEqualTo(Optional.of(voucher2));
	}

	@Test
	@Order(3)
	@DisplayName("모든 바우처 조회하기")
	void findAll() {
		Voucher voucher = list.get(0);
		Voucher voucher2 = list.get(1);
		Voucher voucher3 = list.get(2);

		assertThat(voucherRepository.findAll()).hasSize(3);
		assertThat(voucherRepository.findAll()).contains(voucher);
		assertThat(voucherRepository.findAll()).contains(voucher2);
		assertThat(voucherRepository.findAll()).contains(voucher3);

		Voucher voucher4 = new FixedAmountVoucher(UUID.randomUUID(), 4000);
		assertThat(voucherRepository.findAll()).doesNotContain(voucher4);

	}

	@Test
	@Order(4)
	@DisplayName("바우처 삭제하기")
	void deleteVoucher() {
		Voucher voucher = list.get(0);

		assertThat(voucherRepository.findAll()).contains(voucher);
		voucherRepository.deleteVoucher(voucher);
		assertThat(voucherRepository.findAll()).doesNotContain(voucher);
	}
}