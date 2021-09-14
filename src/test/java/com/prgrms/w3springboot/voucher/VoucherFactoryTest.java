package com.prgrms.w3springboot.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherFactoryTest {
    private static final UUID VOUCHER_ID = UUID.randomUUID();

    @DisplayName("바우처 팩토리에서 바우처 타입에 따라 바우처를 생성한다.")
    @ParameterizedTest
    @EnumSource(value = VoucherType.class)
    void testCreateVoucher(VoucherType voucherType) {
        VoucherFactory voucherFactory = new VoucherFactory();
        Voucher voucher = voucherFactory.createVoucher(VOUCHER_ID, voucherType, 10L);

        assertThat(voucherType).isEqualTo(voucher.getVoucherType());
        assertThat(10L).isEqualTo(voucher.getAmount());
    }
}