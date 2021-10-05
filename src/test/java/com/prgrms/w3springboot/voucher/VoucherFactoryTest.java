package com.prgrms.w3springboot.voucher;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class VoucherFactoryTest {
	private static final UUID VOUCHER_ID = UUID.randomUUID();

	@DisplayName("바우처 팩토리에서 바우처 타입에 따라 바우처를 생성한다.")
	@ParameterizedTest
	@EnumSource(value = VoucherType.class)
	void testCreateVoucher(VoucherType voucherType) {
		Voucher voucher = VoucherFactory.getInstance().createVoucher(VOUCHER_ID, 10L, voucherType, LocalDateTime.now());

		assertThat(voucher.getVoucherType()).isEqualTo(voucherType);
		assertThat(voucher.getAmount()).isEqualTo(10L);
	}
}
