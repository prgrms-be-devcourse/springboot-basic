package org.programmers.springbootbasic.voucher.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

@DisplayName("바우처 서비스 테스트")
class VoucherServiceImplTest {

    private static final VoucherProperty VOUCHER_PROPERTY_MOCK = mock(VoucherProperty.class);
    private static final VoucherRepository VOUCHER_REPOSITORY_MOCK = mock(VoucherRepository.class);
    private static final VoucherServiceImpl VOUCHER_SERVICE = new VoucherServiceImpl(VOUCHER_REPOSITORY_MOCK, VOUCHER_PROPERTY_MOCK);

    private static final Voucher TEST_VOUCHER = Voucher.create(1000, FIXED);

    @BeforeAll
    static void setMocks() {
        when(VOUCHER_PROPERTY_MOCK.getFixed())
                .thenReturn(new VoucherProperty.FixedDiscountVoucherProperty(100000, 500));
        when(VOUCHER_PROPERTY_MOCK.getRate())
                .thenReturn(new VoucherProperty.RateDiscountVoucherProperty(90, 5));
    }

    @AfterEach
    void clear() {
        reset(VOUCHER_REPOSITORY_MOCK);
    }

    @Test
    @DisplayName("바우처 등록하기 - 비정상적인 값")
    void validateVoucher() {
        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(5000000, FIXED));
        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(-2000, FIXED));
        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(0, FIXED));

        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(100, RATE));
        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(-5, RATE));
        assertThrows(IllegalArgumentException.class, () -> VOUCHER_SERVICE.registerVoucher(0, RATE));
    }


    @Test
    @DisplayName("바우처 찾기")
    void getVoucher() {
        when(VOUCHER_REPOSITORY_MOCK.findById((TEST_VOUCHER).getId())).thenReturn(Optional.of(TEST_VOUCHER));

        Voucher foundVoucher = VOUCHER_SERVICE.getVoucher(TEST_VOUCHER.getId());
        assertThat(foundVoucher, is(TEST_VOUCHER));
    }

    @Test
    @DisplayName("바우처 찾기 - 탐색 실패")
    void getVoucherWithNoResult() {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (TEST_VOUCHER.getId().equals(uuid));
        when(VOUCHER_REPOSITORY_MOCK.findById(uuid)).thenReturn(Optional.empty());

        boolean assertionResult = false;
        try {
            VOUCHER_SERVICE.getVoucher(uuid);
        } catch (IllegalArgumentException e) {
            assertionResult = true;
        }

        assertThat(assertionResult, is(true));
    }

    @Test
    @DisplayName("바우처 찾기 실패")
    void getVoucherFailed() {
        assertThrows(IllegalArgumentException.class, () ->
                VOUCHER_SERVICE.getVoucher(UUID.randomUUID()));
    }

    @Test
    @DisplayName("바우처 적용하여 할인가 반환")
    void applyVoucher() {
        Voucher voucher = new FixedDiscountVoucher(UUID.randomUUID(), 1000);
        long beforeDiscount = 10000;
        long discountedPrice = VOUCHER_SERVICE.applyVoucher(beforeDiscount, voucher);

        assertEquals(voucher.discount(beforeDiscount), discountedPrice);
    }

    @Test
    @DisplayName("바우처 소모하기")
    void useVoucher() {
        Voucher voucher;
        do {
            voucher = Voucher.create(1000, FIXED);
        } while (TEST_VOUCHER.getId().equals(voucher.getId()));

        VOUCHER_SERVICE.useVoucher(voucher.getId());

        verify(VOUCHER_REPOSITORY_MOCK, times(1)).remove(voucher.getId());
    }
}