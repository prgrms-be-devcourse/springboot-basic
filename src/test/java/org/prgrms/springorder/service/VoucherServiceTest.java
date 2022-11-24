package org.prgrms.springorder.service;

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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.FixedAmountVoucher;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

	@Mock
	VoucherRepository voucherRepository;

	@InjectMocks
	VoucherService voucherService;

	@Test
	@DisplayName("주어진 voucherType, value로 바우처를 생성하고 저장한다.")
	void createVoucherTest() {

		MockedStatic<VoucherFactory> voucherFactory = Mockito.mockStatic(VoucherFactory.class);
		//given
		VoucherType voucherType = VoucherType.FIXED_AMOUNT;
		double value = 50;

		FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now());

		Mockito.when(VoucherFactory.createVoucher(voucherType, value)).thenReturn(fixedAmountVoucher);
		Mockito.doNothing().when(voucherRepository).save(fixedAmountVoucher);

		//when
		voucherService.createVoucher(voucherType, value);

		//then
		Mockito.verify(voucherRepository).save(fixedAmountVoucher);
		voucherFactory.verify(() -> VoucherFactory.createVoucher(voucherType, value), Mockito.times(1));

	}

	@Test
	@DisplayName("주어진 UUID로 바우처를 단건조회한다.")
	void findByIdTest() {

		//given
		UUID uuid = UUID.randomUUID();
		double value = 50;
		Optional<Voucher> fixedAmountVoucher = Optional.of(
			new FixedAmountVoucher(UUID.randomUUID(), value, LocalDateTime.now()));

		Mockito.when(voucherRepository.findById(uuid)).thenReturn(fixedAmountVoucher);

		//when
		voucherService.findById(uuid);

		//then
		Mockito.verify(voucherRepository).findById(uuid);

	}

	@Test
	@DisplayName("전체 바우처를 조회한다.")
	void getListTest() {

		//given
		double value1 = 50;
		Voucher voucher1 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value1);
		double value2 = 150;
		Voucher voucher2 = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value2);
		List<Voucher> voucherList = new ArrayList<>();

		voucherList.add(voucher1);
		voucherList.add(voucher2);

		Mockito.when(voucherRepository.findAll()).thenReturn(voucherList);

		voucherService.getList();

		Mockito.verify(voucherRepository).findAll();

	}

	@Test
	@DisplayName("바우처를 삭제한다.")
	void deleteById() {

		//given
		double value = 50;
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		Mockito.doNothing().when(voucherRepository).delete(voucher.getVoucherId());

		//when
		voucherService.deleteById(voucher.getVoucherId());

		//then
		Mockito.verify(voucherRepository).delete(voucher.getVoucherId());
	}
}