package com.prgrms.w3springboot.voucher.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

	@Mock
	VoucherFactory voucherFactory;

	@Mock
	CsvVoucherRepository csvVoucherRepository;

	@InjectMocks
	VoucherService voucherService;

	public static Stream<Arguments> provideVouchersForMock() {
		return Stream.of(
			Arguments.of(new FixedAmountVoucher(VOUCHER_ID, 10, VoucherType.FIXED), VoucherType.FIXED),
			Arguments.of(new PercentAmountVoucher(VOUCHER_ID, 10, VoucherType.PERCENT), VoucherType.PERCENT)
		);
	}

	@DisplayName("바우처 아이디로 바우처를 조회한다.")
	@Test
	void testGetVoucherByVoucherId() {
		doReturn(Optional.of(new FixedAmountVoucher(VOUCHER_ID, 10, VoucherType.FIXED)))
			.when(csvVoucherRepository).findById(VOUCHER_ID);

		Voucher voucher = voucherService.getVoucher(VOUCHER_ID);

		verify(csvVoucherRepository).findById(VOUCHER_ID);
		assertThat(voucher.getAmount()).isEqualTo(10L);
	}

	@DisplayName("바우처를 생성한다.")
	@ParameterizedTest
	@MethodSource("provideVouchersForMock")
	void testCreateVoucher(Voucher voucher, VoucherType voucherType) {
		doReturn(voucher)
			.when(voucherFactory).createVoucher(VOUCHER_ID, 10, voucherType);
		doReturn(voucher)
			.when(csvVoucherRepository).insert(voucher);

		Voucher createdVoucher = voucherService.createVoucher(VOUCHER_ID, 10, voucherType);

		assertThat(createdVoucher.getVoucherId()).isEqualTo(VOUCHER_ID);
	}

	@DisplayName("바우처 목록을 조회한다.")
	@Test
	void testListVoucher() {
		doReturn(List.of(
			new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherType.FIXED),
			new PercentAmountVoucher(UUID.randomUUID(), 30, VoucherType.PERCENT),
			new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherType.FIXED)
		)).when(csvVoucherRepository).findAll();

		List<Voucher> voucherList = voucherService.listVoucher();

		verify(csvVoucherRepository).findAll();
		assertThat(voucherList.size()).isEqualTo(3);
	}
}