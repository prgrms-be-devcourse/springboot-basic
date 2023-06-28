package com.devcourse.springbootbasic.application.domain.voucher.repository;

import com.devcourse.springbootbasic.application.domain.voucher.data.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.data.PercentDiscountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import com.devcourse.springbootbasic.application.domain.voucher.repository.MemoryVoucherRepository;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
class MemoryVoucherRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = "com.devcourse.springbootbasic.application.repository"
    )
    static class Config {}

    @Autowired
    MemoryVoucherRepository memoryVoucherRepository;

    private static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(
                        new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 100),
                        new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, 23)
                )
        );
    }

    @ParameterizedTest
    @DisplayName("바우처 insert 시 메모리 맵에 추가한다")
    @MethodSource("provideVouchers")
    void insert(Voucher voucher) {
        int count = memoryVoucherRepository.findAll().size();
        memoryVoucherRepository.insert(voucher);
        assertThat(memoryVoucherRepository.findAll(), notNullValue());
        assertThat(memoryVoucherRepository.findAll().size(), is(count+1));
    }

    @Test
    @DisplayName("모든 바우처를 리스트로 반환한다.")
    void findAll() {
        assertThat(memoryVoucherRepository.findAll(), notNullValue());
        assertThat(memoryVoucherRepository.findAll(), not(empty()));
    }
}