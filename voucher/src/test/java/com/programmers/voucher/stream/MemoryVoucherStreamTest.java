package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryVoucherStreamTest {

    private final VoucherStream voucherStream = new MemoryVoucherStream();
    @Test
    @DisplayName("데이터 저장 로직 정상 작동 검증")
    void memoryVoucherStream() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID().toString(), 10000);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID().toString(), 70);

        // when
        voucherStream.save(voucher1);
        voucherStream.save(voucher2);

        // then
        assertThat(voucherStream.findAll().size()).isEqualTo(2);
        assertThat(voucherStream.findAll().keySet()).contains(voucher1.getVoucherId());

    }

}
