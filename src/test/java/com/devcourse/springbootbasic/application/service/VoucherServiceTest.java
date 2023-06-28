package com.devcourse.springbootbasic.application.service;

import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.repository.voucher.MemoryVoucherRepository;
import com.devcourse.springbootbasic.application.repository.voucher.VoucherRepository;
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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
class VoucherServiceTest {

    static Stream<Arguments> provideVoucherDto() {
        return Stream.of(
                Arguments.of(new VoucherDto(VoucherType.FIXED_AMOUNT, 10)),
                Arguments.of(new VoucherDto(VoucherType.PERCENT_DISCOUNT, 4))
        );
    }

    @ParameterizedTest
    @DisplayName("바우처생성 성공하면 예외 없음")
    @MethodSource("provideVoucherDto")
    void 바우처생성테스트(VoucherDto voucherDto) {
        var voucherRepository = new MemoryVoucherRepository();
        var voucherService = new VoucherService(voucherRepository);
        assertDoesNotThrow(() -> voucherService.createVoucher(voucherDto));
    }

}