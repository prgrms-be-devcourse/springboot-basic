package org.prgrms.springorder.service;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.PercentDiscountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.repository.voucher.VoucherRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

	private VoucherService voucherService;

	private VoucherRepository voucherRepository;

	private MockedStatic<VoucherFactory> voucherFactory;

	@BeforeAll
	public void beforeAll() {
		voucherFactory = mockStatic(VoucherFactory.class);
		voucherRepository = mock(VoucherRepository.class);
		voucherService = new VoucherService(voucherRepository);
	}

	@AfterAll
	public void afterAll() {
		voucherFactory.close();
	}

	@Test
	@DisplayName("바우처를 성공적으로 생성한다.")
	void test1() {
		//given
		VoucherType voucherType = VoucherType.FIXED_AMOUNT;
		double value = 50;

		FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), value);

		when(VoucherFactory.createVoucher(voucherType, value))
			.thenReturn(fixedAmountVoucher);

		doNothing().when(voucherRepository)
			.save(fixedAmountVoucher);

		//when
		voucherService.createVoucher(voucherType, value);

		//then
		verify(voucherRepository).save(fixedAmountVoucher);
		voucherFactory.verify(() -> VoucherFactory.createVoucher(voucherType, value), times(1));
	}

	@Test
	@DisplayName("바우처 리스트를 받아서 바우처를 스트링으로 변환하여 성공적으로 반환한다. ")
	void test2() {

		//given
		List<Voucher> voucherList = new ArrayList<>();
		FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50);
		PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 40);
		voucherList.add(fixedAmountVoucher);
		voucherList.add(percentDiscountVoucher);

		List<String> convertedToStringVoucherList = voucherList.stream()
			.map(Objects::toString).toList();
		int size = 2;

		when(voucherRepository.findAll())
			.thenReturn(voucherList);

		//when
		List<String> vouchers = voucherService.getList();

		//then
		Assertions.assertNotNull(vouchers);
		Assertions.assertEquals(size, voucherList.size());
		Assertions.assertLinesMatch(convertedToStringVoucherList, vouchers);
		verify(voucherRepository).findAll();

	}
}