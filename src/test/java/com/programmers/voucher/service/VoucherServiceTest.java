package com.programmers.voucher.service;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VoucherServiceTest {
    VoucherService voucherService;
    MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @AfterEach
    void afterEach() {
        voucherRepository.clear();
    }

    @ParameterizedTest
    @DisplayName("바우처 타입에 따른 바우처 생성에 성공한다.")
    @MethodSource("createVoucherArguments")
    void 바우처_생성(VoucherType voucherType, long amount, Class<? extends Voucher> expected) {
        Voucher voucher = voucherService.createVoucher(voucherType, new DiscountAmount(voucherType, amount));
        assertThat(voucher.getClass()).isEqualTo(expected);
    }

    private static Stream<Arguments> createVoucherArguments() {
        return Stream.of(
                arguments(VoucherType.FIXED_AMOUNT, 3000L, FixedAmountVoucher.class),
                arguments(VoucherType.PERCENT_DISCOUNT, 30L, PercentDiscountVoucher.class)
        );
    }
}
