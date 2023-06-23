package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import com.programmers.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class VoucherServiceTest {
    VoucherRepository voucherRepository = new MemoryVoucherRepository();
    VoucherService voucherService = new VoucherService(voucherRepository);

    @DisplayName("VoucherService 바우처 생성 및 조회 테스트")
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

        VoucherRequestDto voucherRequestDto = new VoucherRequestDto(voucherType, discountAmount);
        Voucher voucher = voucherService.create(voucherRequestDto);
    }

}