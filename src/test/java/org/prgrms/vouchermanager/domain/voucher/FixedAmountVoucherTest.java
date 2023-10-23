package org.prgrms.vouchermanager.domain.voucher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.repository.VoucherRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {
    @Test
    @DisplayName("할인금액이 잘 적용되는지 테스트")
    void discount(){
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        long discountPrice = fixedAmountVoucher.discount(100);
        Assertions.assertThat(discountPrice).isEqualTo(90L);
    }
}