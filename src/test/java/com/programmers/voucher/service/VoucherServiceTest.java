package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherFactory;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {
    VoucherRepository voucherRepository;
    VoucherServiceImpl voucherService;

    @BeforeEach
    public void setUp() {
        voucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherServiceImpl(voucherRepository, new VoucherFactory());
    }

    @DisplayName("바우처 타입과 할인값에 대한 바우처 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {
            "2, 10"
            , "2, 50"
            , "1, 50"
            , "1, 41234123512"
            , "0, 20"
            , "0, 0"
            , "2, 0"
            , "2, 100"
            , "1, 999999"
            , "1, 30"
    })
    void createVoucherAndFindVouchers(String voucherType, long discountAmount) {

        VoucherRequestDto requestDto = new VoucherRequestDto(voucherType, discountAmount);
        UUID voucherId = voucherService.create(requestDto);
        assertThat(voucherId).isNotNull();
    }
}