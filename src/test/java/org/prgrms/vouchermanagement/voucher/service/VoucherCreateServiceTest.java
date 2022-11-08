package org.prgrms.vouchermanagement.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherCreateServiceTest {

    @Autowired
    private VoucherCreateService voucherCreateService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("FixedAmountVoucher 정상 생성 확인")
    void createFixedAmountVoucher() {
        // given
        String voucherTypeInput = "1";
        int discountValue = 1000;

        // when
        Voucher voucher = voucherCreateService.createVoucher(voucherTypeInput, discountValue);

        // then
        Assertions.assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        Assertions.assertThat(voucher.getDiscountAmount()).isEqualTo(discountValue);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 정상 생성 확인")
    void createPercentDiscountVoucher() {
        // given
        String voucherTypeInput = "2";
        int discountValue = 50;

        // when
        Voucher voucher = voucherCreateService.createVoucher(voucherTypeInput, discountValue);

        // then
        Assertions.assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.PERCENT_DISCOUNT);
        Assertions.assertThat(voucher.getDiscountAmount()).isEqualTo(discountValue);
    }

}