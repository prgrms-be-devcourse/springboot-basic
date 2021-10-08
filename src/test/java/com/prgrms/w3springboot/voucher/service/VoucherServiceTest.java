package com.prgrms.w3springboot.voucher.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
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

import com.prgrms.w3springboot.voucher.FixedAmountVoucher;
import com.prgrms.w3springboot.voucher.PercentAmountVoucher;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherFactory;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.prgrms.w3springboot.voucher.repository.CsvVoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
	private static final UUID VOUCHER_ID = UUID.randomUUID();
	private static final LocalDateTime NOW = LocalDateTime.now();

	@Mock
	private CsvVoucherRepository csvVoucherRepository;

	@InjectMocks
	private VoucherService voucherService;

	@DisplayName("바우처 아이디로 바우처를 조회한다.")
	@Test
	void testGetVoucherByVoucherId() {
		doReturn(Optional.of(new FixedAmountVoucher(VOUCHER_ID, 10, VoucherType.FIXED, NOW)))
			.when(csvVoucherRepository).findById(VOUCHER_ID);

		Voucher voucher = voucherService.getVoucher(VOUCHER_ID);

		verify(csvVoucherRepository).findById(VOUCHER_ID);
		assertThat(voucher.getAmount()).isEqualTo(10L);
	}

	@DisplayName("바우처를 생성한다.")
	@Test
	void testCreateVoucher() {
		Voucher voucher = new FixedAmountVoucher(VOUCHER_ID, 10, VoucherType.FIXED, NOW);
		MockedStatic<VoucherFactory> voucherFactoryMockedStatic = mockStatic(VoucherFactory.class);
		VoucherFactory voucherFactoryMock = mock(VoucherFactory.class);
		voucherFactoryMockedStatic.when(VoucherFactory::getInstance).thenReturn(voucherFactoryMock);
		when(voucherFactoryMock.createVoucher(VOUCHER_ID, 10, VoucherType.FIXED, NOW)).thenReturn(voucher);
		doReturn(voucher).when(csvVoucherRepository).insert(voucher);

		Voucher createdVoucher = voucherService.createVoucher(VOUCHER_ID, 10, VoucherType.FIXED, NOW);

		assertThat(createdVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
	}

	@DisplayName("바우처 목록을 조회한다.")
	@Test
	void testListVoucher() {
		doReturn(List.of(
			new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherType.FIXED, NOW),
			new PercentAmountVoucher(UUID.randomUUID(), 30, VoucherType.PERCENT, NOW),
			new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherType.FIXED, NOW)
		)).when(csvVoucherRepository).findAll();

		List<Voucher> voucherList = voucherService.listVoucher();

		verify(csvVoucherRepository).findAll();
		assertThat(voucherList.size()).isEqualTo(3);
	}
}
