package org.programmers.springbootbasic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.repository.TestVoucherRepository;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.service.VoucherServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("바우처 서비스 테스트")
class VoucherServiceImplTest {

    private static VoucherProperty voucherProperty = new VoucherProperty();
    private static final TestVoucherRepository voucherRepository = new TestVoucherRepository();
    private static final VoucherServiceImpl voucherService = new VoucherServiceImpl(voucherRepository, voucherProperty);

    @BeforeAll
    static void setVoucherProperty() {
        voucherProperty.setFixed(new VoucherProperty.FixedDiscountVoucherProperty(100000, 100));
    }

    @AfterEach
    void clear() {
        voucherRepository.clear();
    }

    @Test
    @DisplayName("바우처 등록하기")
    void registerVoucher() {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
        Voucher registeredVoucher = voucherService.registerVoucher(voucher);

        assertEquals(voucher, registeredVoucher);
    }

    @Test
    @DisplayName("바우처 찾기")
    void getVoucher() {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
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
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
        long beforeDiscount = 10000;
        long discountedPrice = voucherService.applyVoucher(beforeDiscount, voucher);

        assertEquals(voucher.discount(beforeDiscount), discountedPrice);
    }

    @Test
    @DisplayName("바우처 소모하기")
    void useVoucher() {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
        voucherService.registerVoucher(voucher);
        voucherService.useVoucher(voucher.getId());

        assertThrows(IllegalArgumentException.class, () ->
                voucherService.getVoucher(voucher.getId()));
    }
}