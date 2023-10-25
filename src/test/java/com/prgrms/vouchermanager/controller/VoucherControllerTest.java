package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VoucherControllerTest {

    @Autowired
    private VoucherController controller;
    @Test
    @DisplayName("create - fixed")
    void createFixedTest() {
        Voucher voucher = controller.create(VoucherType.FIXED, 20000);
        assertThat(voucher instanceof FixedAmountVoucher).isTrue();
        assertThat(voucher.getDiscount()).isEqualTo(20000);
    }

    @Test
    @DisplayName("create - percent")
    void createPercentTest() {
        Voucher voucher = controller.create(VoucherType.PERCENT, 20);
        assertThat(voucher instanceof PercentAmountVoucher).isTrue();
        assertThat(voucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("list")
    void listTest() {
        List<Voucher> list = controller.list();
        assertThat(list.size()).isEqualTo(3);
    }
}
