package org.prgrms.springorder.service;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.repository.voucher.VoucherRepository;
import org.prgrms.springorder.service.voucher.VoucherService;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

	@Mock
	VoucherRepository voucherRepository;

	@InjectMocks
	VoucherService voucherService;

	@Test
	@DisplayName("주어진 voucherType, value로 바우처를 생성하고 성공적으로 저장한다.")
	void createVoucherTest() {

		MockedStatic<VoucherFactory> voucherFactory = mockStatic(VoucherFactory.class);
		//given
		VoucherType voucherType = VoucherType.FIXED_AMOUNT;
		double value = 50;

		FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());

		when(VoucherFactory.createVoucher(voucherType, value)).thenReturn(fixedAmountVoucher);
		doNothing().when(voucherRepository).save(fixedAmountVoucher);

		//when
		voucherService.createVoucher(voucherType, value);

		//then
		verify(voucherRepository).save(fixedAmountVoucher);
		voucherFactory.verify(() -> VoucherFactory.createVoucher(voucherType, value), times(1));

	}

	@Test
	@DisplayName("주어진 UUID로 바우처를 성공적으로 단건조회한다.")
	void findByIdTest() {

		//given
		UUID uuid = UUID.randomUUID();
		double value = 50;
		Optional<Voucher> fixedAmountVoucher = Optional.of(
			new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now()));

		when(voucherRepository.findById(uuid)).thenReturn(fixedAmountVoucher);

		//when
		voucherService.findById(uuid);

		//then
		verify(voucherRepository).findById(uuid);

	}

	@Test
	@DisplayName("전체 바우처를 성공적으로 조회한다.")
	void getListTest() {

		//given
		double value1 = 50;
		Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value1);
		double value2 = 150;
		Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value2);
		List<Voucher> voucherList = new ArrayList<>();

		voucherList.add(voucher1);
		voucherList.add(voucher2);

		when(voucherRepository.findAll()).thenReturn(voucherList);

		voucherService.getList();

		verify(voucherRepository).findAll();

	}

	@Test
	@DisplayName("바우처를 성공적으로 삭제한다.")
	void deleteById() {

		//given
		double value = 50;
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		doNothing().when(voucherRepository).deleteById(voucher.getVoucherId());

		//when
		voucherService.deleteById(voucher.getVoucherId());

		//then
		verify(voucherRepository).deleteById(voucher.getVoucherId());
	}
}