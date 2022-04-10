package org.programmers.springbootbasic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.repository.MemoryVoucherRepository;
import org.programmers.springbootbasic.repository.TestVoucherRepository;
import org.programmers.springbootbasic.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.TestVoucher;
import org.programmers.springbootbasic.voucher.Voucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("바우처 서비스 테스트")
class VoucherServiceImplTest {

    private static final TestVoucherRepository voucherRepository = new TestVoucherRepository();
    private static final VoucherServiceImpl voucherService = new VoucherServiceImpl(voucherRepository);

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("바우처 등록하기")
    void registerVoucher() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
        Voucher registeredVoucher = voucherService.registerVoucher(voucher);

        assertEquals(voucher, registeredVoucher);
    }

    @Test
    @DisplayName("바우처 찾기")
    void getVoucher() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
        voucherService.registerVoucher(voucher);

        Voucher foundVoucher = voucherService.getVoucher(voucher.getId());

        assertEquals(voucher, foundVoucher);
    }

    @Test
    @DisplayName("바우처 찾기 실패")
    void getVoucherFailed() {
        assertThrows(IllegalArgumentException.class, () ->
                voucherService.getVoucher(UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처 적용하여 할인가 반환")
    void applyVoucher() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
        long beforeDiscount = 10000;
        long discountedPrice = voucherService.applyVoucher(beforeDiscount, voucher);

        assertEquals(voucher.discount(beforeDiscount), discountedPrice);
    }

    @Test
    @DisplayName("바우처 소모하기")
    void useVoucher() {
        Voucher voucher = new TestVoucher(UUID.randomUUID());
        voucherService.registerVoucher(voucher);
        voucherService.useVoucher(voucher.getId());

        assertThrows(IllegalArgumentException.class, () ->
                voucherService.getVoucher(voucher.getId()));
    }
}