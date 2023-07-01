package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.domain.voucher.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.PercentDiscountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.repository.voucher.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("default")
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    VoucherService voucherService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository);
    }

    static List<Voucher> vouchers = List.of(
            new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100")),
            new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")),
            new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240")),
            new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"))
    );

    @ParameterizedTest
    @DisplayName("바우처 생성 테스트")
    @MethodSource("provideVouchers")
    void testCreateVoucher(Voucher voucher) {
        when(voucherRepository.insert(voucher)).thenReturn(voucher);
        var result = voucherService.createVoucher(voucher);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
        assertThat(result.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 리스트 반환 테스트")
    void testGetVouchers() {
        when(voucherRepository.findAll()).thenReturn(vouchers);
        var result = voucherService.getVouchers();
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Voucher.class));
    }

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

}