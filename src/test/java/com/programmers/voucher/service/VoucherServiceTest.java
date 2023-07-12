package com.programmers.voucher.service;

import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.domain.FixedDiscount;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherServiceTest {

    private VoucherService voucherService = new VoucherServiceImpl(new MemoryVoucherRepository());

    @DisplayName("바우처 타입과 할인값으로 바우처를 생성하고 바우처를 반환")
    @ParameterizedTest
    @CsvSource(value = {
            "2, 10"
            , "2, 50"
            , "1, 50"
            , "2, 100"
            , "1, 999999"
            , "1, 30"
    })
    void createVoucherTest(String command, long value) {
        Discount discount = Discount.of(DiscountType.of(command), value);
        UUID id = UUID.randomUUID();
        VoucherRequestDto requestDto = new VoucherRequestDto(id, discount);

        VoucherResponseDto voucher = voucherService.create(requestDto);

        assertThat(voucher).isNotNull();
    }

    @DisplayName("바우처를 생성했던 ID로 바우처 조회가 가능한지")
    @Test
    void findByIdTest() {
        Discount discount = new FixedDiscount(123456);
        UUID id = UUID.randomUUID();
        VoucherRequestDto requestDto = new VoucherRequestDto(id, discount);

        VoucherResponseDto expected = voucherService.create(requestDto);
        VoucherResponseDto actual = voucherService.findVoucherById(id);

        assertThat(expected).isEqualTo(actual);
    }
}
