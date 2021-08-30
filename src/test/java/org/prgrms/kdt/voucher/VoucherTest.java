package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:42 오후
 */
class VoucherTest {

    @Test
    @DisplayName("바우처 생성 테스트")
    void createVoucher() {
        UUID voucherId = UUID.randomUUID();
        String name = "test voucher";
        Long discount = 50L;
        VoucherType voucherType = VoucherType.PERCENT;
        LocalDateTime createdAt = LocalDateTime.now();

        Voucher voucher = new Voucher(voucherId, name, discount, voucherType, createdAt);

        assertThat(voucher.getDiscount()).isEqualTo(50L);
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(voucher.getDiscount()).isEqualTo(50L);
    }

}