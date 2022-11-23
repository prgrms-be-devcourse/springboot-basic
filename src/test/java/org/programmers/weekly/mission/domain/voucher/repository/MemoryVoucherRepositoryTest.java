package org.programmers.weekly.mission.domain.voucher.repository;

import org.junit.jupiter.api.*;
import org.programmers.weekly.mission.domain.voucher.model.FixedAmountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherRepositoryTest {

    private static final VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private static final Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    private static final Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

    @Test
    @Order(1)
    @DisplayName("바우처를 추가할 수 있다")
    void testInsert() {
        Voucher voucher1 = voucherRepository.insert(fixedAmountVoucher);
        Voucher voucher2 = voucherRepository.insert(percentDiscountVoucher);
        assertSame(fixedAmountVoucher, voucher1);
        assertSame(percentDiscountVoucher, voucher2);
    }

    @Test
    @Order(2)
    @DisplayName("바우처를 조회할 수 있다")
    void getVoucherList() {
        List<Voucher> vouchers = voucherRepository.getVoucherList();
        assertThat(vouchers, hasSize(2));
    }

    @Test
    @Order(3)
    @DisplayName("바우처를 아이디로 조회할 수 있다")
    void findById() {
        Optional<Voucher> voucher = voucherRepository.findById(fixedAmountVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(false));
        assertSame(fixedAmountVoucher, voucher.get());
    }
}