package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.model.DiscountValue;
import com.devcourse.springbootbasic.application.model.VoucherType;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;

@ActiveProfiles("dev")
class VoucherServiceTest {

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "0")),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "1240")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "10"))
    );
    @Mock
    VoucherRepository voucherRepository;
    VoucherService voucherService;

    static Stream<Arguments> provideVouchers() {
        return vouchers.stream()
                .map(Arguments::of);
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        voucherService = new VoucherService(voucherRepository);
    }

    @ParameterizedTest
    @DisplayName("바우처 생성 테스트")
    @MethodSource("provideVouchers")
    void testCreateVoucher(Voucher voucher) {
        given(voucherRepository.insert(voucher)).willReturn(voucher);
        var result = voucherService.createVoucher(voucher);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
        assertThat(result.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("바우처 리스트 반환 테스트")
    void testGetVouchers() {
        given(voucherRepository.findAll()).willReturn(vouchers);
        var result = voucherService.getVouchers();
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Voucher.class));
    }

}