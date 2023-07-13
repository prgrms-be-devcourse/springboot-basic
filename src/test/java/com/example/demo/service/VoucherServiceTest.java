package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.domain.voucher.MemoryVoucherRepository;
import com.example.demo.dto.voucher.VoucherResponseDto;
import com.example.demo.util.VoucherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherServiceTest {

    VoucherService voucherService;

    @BeforeEach
    void beforeEach() {
        voucherService = new VoucherService(new MemoryVoucherRepository());
    }

    @DisplayName("정액 바우처 읽어오기 테스트")
    @ParameterizedTest
    @ValueSource(ints = {100, 500, 3340})
    void 바우처_읽기_테스트1(int amount) {
        //given
        VoucherResponseDto voucherResponseDto = voucherService.save(VoucherType.FIX, amount);

        //when
        VoucherResponseDto voucherResponseDtoReaded = voucherService.read(voucherResponseDto.getId());

        //then
        assertThat(voucherResponseDtoReaded.getId().toString()).isEqualTo(voucherResponseDto.getId().toString());
        assertThat(voucherResponseDtoReaded.getDiscountAmount()).isEqualTo(amount);
        assertThat(voucherResponseDtoReaded.getVoucherType()).isEqualTo(VoucherType.FIX);
    }

    @DisplayName("정율 바우처 읽어오기 테스트")
    @ParameterizedTest
    @ValueSource(ints = {30, 50, 100})
    void 바우처_읽기_테스트2(int amount) {
        //given
        VoucherResponseDto voucherResponseDto = voucherService.save(VoucherType.PERCENT, amount);

        //when
        VoucherResponseDto voucherResponseDtoReaded = voucherService.read(voucherResponseDto.getId());

        //then
        assertThat(voucherResponseDtoReaded.getId().toString()).isEqualTo(voucherResponseDto.getId().toString());
        assertThat(voucherResponseDtoReaded.getDiscountAmount()).isEqualTo(amount);
        assertThat(voucherResponseDtoReaded.getVoucherType()).isEqualTo(VoucherType.PERCENT);
    }
}