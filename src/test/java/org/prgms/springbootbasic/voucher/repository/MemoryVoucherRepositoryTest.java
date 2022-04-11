package org.prgms.springbootbasic.voucher.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.vo.FixedAmountVoucher;
import org.prgms.springbootbasic.voucher.vo.PercentDiscountVoucher;
import org.prgms.springbootbasic.voucher.vo.Voucher;

class MemoryVoucherRepositoryTest {

	VoucherRepository voucherRepository;

	@BeforeEach
	void setUp() {
		voucherRepository = new MemoryVoucherRepository();
	}

	@DisplayName("repository 저장(save) 성공 테스트")
	@Test
	void save_pass_test() {
		//given
		Voucher amountVoucher = new FixedAmountVoucher(10L);
		//when
		Voucher stored = voucherRepository.save(amountVoucher);
		//then
		assertEquals(amountVoucher.getVoucherId(), stored.getVoucherId());

		//given
		Voucher percentVoucher = new FixedAmountVoucher(10L);
		//when
		Voucher percentStored = voucherRepository.save(percentVoucher);
		//then
		assertEquals(percentVoucher.getVoucherId(), percentStored.getVoucherId());
	}

	@DisplayName("repository save 실패 테스트")
	@Test
	void save_fail_test() {
		// null을 저장할 수는 없다.
		assertThrows(IllegalArgumentException.class, () -> voucherRepository.save(null));
	}

	@DisplayName("Voucher 조회 성공 테스트")
	@Test
	void get_Voucher_pass_test() {
		// 아무것도 저장되어있지 않을 때 0
		assertEquals(0, voucherRepository.getVoucherListByType().size());

		// Voucher의 종류 test
		//given
		voucherRepository.save(new FixedAmountVoucher(10L));
		voucherRepository.save(new PercentDiscountVoucher(20));
		//when
		Map<String, List<Voucher>> voucherList = voucherRepository.getVoucherListByType();
		//then
		assertEquals(2, voucherList.size());

		//given
		voucherRepository.save(new FixedAmountVoucher(30L));
		//when

		Map<String, List<Voucher>> voucherList1 = voucherRepository.getVoucherListByType();
		//then
		assertEquals(2, voucherList1.get(FixedAmountVoucher.class.getSimpleName()).size());
		assertEquals(1, voucherList1.get(PercentDiscountVoucher.class.getSimpleName()).size());

	}
}
