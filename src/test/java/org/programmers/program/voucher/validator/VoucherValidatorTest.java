package org.programmers.program.voucher.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.program.voucher.model.FixedAmountVoucher;
import org.programmers.program.voucher.model.PercentDiscountVoucher;
import org.programmers.program.voucher.model.Voucher;
import org.programmers.program.voucher.model.VoucherType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherValidatorTest {
    VoucherValidator validator = new VoucherValidator();
    @Test
    @DisplayName("바우처 생성 전 유효성 검증 테스트")
    void validateTest(){
        List<Voucher> vouchers = new ArrayList<>();
        int availableVoucherCount = 0, unAvailableVoucherCount = 0;

        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 20L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 20L));

        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 0L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 0L));

        vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 10000000L));
        vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 150L));

        for(Voucher v: vouchers){
            if (validator.test(
                    v.getClass().getName().equals(FixedAmountVoucher.class.getName()) ? VoucherType.FIXED : VoucherType.PERCENT,
                    v.getDiscountAmount())
            )
                availableVoucherCount += 1;
            else
                unAvailableVoucherCount += 1;
        }
        assertThat(availableVoucherCount).isEqualTo(2);
        assertThat(unAvailableVoucherCount).isEqualTo(4);
    }
}