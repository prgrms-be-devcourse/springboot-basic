package org.prgms.springbootbasic.voucher.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.voucher.repository.voucher.MemoryVoucherRepository;
import org.prgms.springbootbasic.voucher.repository.voucher.VoucherRepository;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
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
		Voucher amountVoucher = new FixedAmountVoucher(100);
		//when,then
		assertSaveVoucherTest(amountVoucher);

		//given
		Voucher percentVoucher = new PercentDiscountVoucher(10);
		//when,then
		assertSaveVoucherTest(percentVoucher);
	}

	private void assertSaveVoucherTest(Voucher voucher) {
		//when
		UUID AmountstoredId = voucherRepository.save(voucher);
		Voucher foundAmountVoucher = voucherRepository.findById(AmountstoredId);
		//then
		assertEquals(voucher.getVoucherId(), foundAmountVoucher.getVoucherId());
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
		voucherRepository.save(new FixedAmountVoucher(100));
		voucherRepository.save(new PercentDiscountVoucher(20));

		//when
		Map<VoucherType, List<Voucher>> voucherList = voucherRepository.getVoucherListByType();
		//then
		assertEquals(2, voucherList.size());

		//given
		voucherRepository.save(new FixedAmountVoucher(300));
		//when

		Map<VoucherType, List<Voucher>> voucherList1 = voucherRepository.getVoucherListByType();
		//then
		assertEquals(2, voucherList1.get(VoucherType.FIXEDAMOUNTVOUCHER).size());
		assertEquals(1, voucherList1.get(VoucherType.PERCENTDISCOUNTVOUCHER).size());

	}

	@DisplayName("findById 실패 테스트 - 존재하지 않는 ID를 입력한 경우")
	@Test
	void findById_fail_test_invalid_ID() {
		assertThrows(IllegalArgumentException.class, () -> voucherRepository.findById(UUID.randomUUID()));
	}
}
