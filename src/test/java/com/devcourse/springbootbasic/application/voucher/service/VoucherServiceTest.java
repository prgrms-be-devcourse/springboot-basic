package com.devcourse.springbootbasic.application.voucher.service;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import com.devcourse.springbootbasic.application.voucher.repository.VoucherRepository;
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
    @DisplayName("새로운 바우처가 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void CreateVoucher_VoucherParam_InsertAndReturnVoucher(Voucher voucher) {
        given(voucherRepository.insert(voucher)).willReturn(voucher);
        var result = voucherService.createVoucher(voucher);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
        assertThat(result.getVoucherId(), is(voucher.getVoucherId()));
    }

    @Test
    @DisplayName("생성된 바우처가 리스트 형태로 반환되면 성공한다.")
    void GetVouchers_VoucherMap_ReturnVouchers() {
        given(voucherRepository.findAllVouchers()).willReturn(vouchers);
        var result = voucherService.getVouchers();
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(Voucher.class));
    }

    static List<Voucher> vouchers = List.of(
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, "100")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, "0")),
            new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, "1240")),
            new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, "10"))
    );

}
