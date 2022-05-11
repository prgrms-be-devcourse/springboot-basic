package org.programmers.voucher.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;
    @Autowired
    VoucherRepository voucherRepository;

    @AfterEach
    void clearRepository() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 생성 및 확인")
    void makeAndListVoucher() {
        voucherService.makeVoucher(VoucherType.FIXED_AMOUNT_VOUCHER, 1000L);
        assertThat(voucherService.listVoucher().size()).isEqualTo(1);
        voucherService.makeVoucher(VoucherType.FIXED_AMOUNT_VOUCHER, 1000L);
        assertThat(voucherService.listVoucher().size()).isEqualTo(2);
    }
}