package org.programmers.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.voucher.domain.FixedAmountVoucher;
import org.programmers.voucher.domain.PercentDiscountVoucher;
import org.programmers.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemoryVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @BeforeEach
    void clear() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 추가")
    void addTest() {
        Voucher fixed = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        voucherRepository.save(fixed);
        List<Voucher> database = voucherRepository.findAll();
        assertThat(database.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("바우처 ID로 조회")
    void findByIdTest() {
        UUID id = UUID.randomUUID();
        Voucher fixed = new FixedAmountVoucher(id, 1000);
        voucherRepository.save(fixed);
        Optional<Voucher> voucherOptional = voucherRepository.findById(id);
        assertThat(voucherOptional.isEmpty()).isFalse();
        assertThat(voucherOptional.get().getVoucherId()).isEqualTo(id);
    }

    @Test
    @DisplayName("바우처 모두 찾기")
    void findAllTest() {
        Voucher fixed = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher percent = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        voucherRepository.save(fixed);
        voucherRepository.save(percent);
        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers.size()).isEqualTo(2);
    }

}