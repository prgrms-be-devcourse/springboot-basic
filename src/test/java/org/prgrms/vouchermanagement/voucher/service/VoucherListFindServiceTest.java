package org.prgrms.vouchermanagement.voucher.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.PercentDiscountVoucher;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherListFindServiceTest {

    @Autowired
    private VoucherListFindService voucherListFindService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    @DisplayName("바우처 리스트 정상 출력 확인")
    void voucherList() {
        // given
        UUID fixedAmountVoucherId = UUID.randomUUID();
        int fixedAmountVoucherDiscountAmount = 1000;
        Voucher fixedAmountVoucher = new FixedAmountVoucher(fixedAmountVoucherId, fixedAmountVoucherDiscountAmount);

        UUID percentDiscountVoucherId = UUID.randomUUID();
        int percentDiscountVoucherDiscountAmount = 50;
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(percentDiscountVoucherId, percentDiscountVoucherDiscountAmount);

        voucherRepository.save(fixedAmountVoucher);
        voucherRepository.save(percentDiscountVoucher);

        // when
        List<Voucher> resultVouchers = voucherListFindService.findAllVouchers();

        // then
        for (Voucher resultVoucher : resultVouchers) {
            if (resultVoucher instanceof FixedAmountVoucher) {
                Assertions.assertThat(resultVoucher).isEqualTo(fixedAmountVoucher);
            } else
                Assertions.assertThat(resultVoucher).isEqualTo(percentDiscountVoucher);
        }
    }
}