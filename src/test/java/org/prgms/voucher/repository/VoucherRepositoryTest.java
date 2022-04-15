package org.prgms.voucher.repository;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.TestConfig;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringJUnitConfig(value = TestConfig.class)
class VoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    @AfterEach
    void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void saveTest() {
        Voucher voucher = new FixedAmountVoucher(10L, UUID.randomUUID());
        voucherRepository.save(voucher);
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers, Matchers.contains(voucher));
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void findAllTest() {
        for (int i = 0; i < 3; i++) {
            voucherRepository.save(new FixedAmountVoucher(10L, UUID.randomUUID()));
        }
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers, Matchers.hasSize(3));
    }
}