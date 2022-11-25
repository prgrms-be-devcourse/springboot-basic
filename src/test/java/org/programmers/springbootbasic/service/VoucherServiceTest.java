package org.programmers.springbootbasic.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.programmers.springbootbasic.domain.voucher.model.Voucher;
import org.programmers.springbootbasic.domain.voucher.VoucherFactory;
import org.programmers.springbootbasic.domain.voucher.dto.VoucherInputDto;
import org.programmers.springbootbasic.domain.voucher.service.VoucherService;
import org.programmers.springbootbasic.exception.WrongRangeInputException;
import org.programmers.springbootbasic.domain.voucher.repository.MemoryVoucherRepository;

class VoucherServiceTest {
    VoucherService voucherService;

    @BeforeEach
    void init() {
        voucherService = new VoucherService(new MemoryVoucherRepository(), new VoucherFactory());
    }


    @Test
    @DisplayName("Voucher 타입 문자열에 맞게 Voucher를 생성해야 한다.")
    void 바우처_생성_타입_확인_테스트() throws Exception {
        //given
        VoucherInputDto fixedVoucherInputDto = new VoucherInputDto("fixed", 1000);
        VoucherInputDto percentVoucherInputDto = new VoucherInputDto("percent", 10);

        // when
        Voucher fixedVoucher = voucherService.createVoucher(fixedVoucherInputDto);
        Voucher percentVoucher = voucherService.createVoucher(percentVoucherInputDto);

        //then
        Assertions.assertThat(fixedVoucher).isInstanceOf(FixedAmountVoucher.class);
        Assertions.assertThat(percentVoucher).isInstanceOf(PercentDiscountVoucher.class);

    }

    @Test
    @DisplayName("생성된 Voucher가 올바르게 저장되어야 한다.")
    void 바우처_생성시_저장_테스트() throws Exception {
        //given
        Voucher percentVoucher = voucherService.createVoucher(new VoucherInputDto("percent", 20));

        //when
        Voucher savedVoucher = voucherService.findAll().get(0);

        //then
        Assertions.assertThat(percentVoucher).isEqualTo(savedVoucher);
    }

    @Test
    @DisplayName("잘못된 바우처 amount 입력 시 WrongRangeInputException을 발생시켜야 한다.")
    void 바우처_생성_실패_테스트() throws Exception {
        //given
        VoucherInputDto voucherDto = new VoucherInputDto("percent", 120);
        // when

        //then
        Assertions.assertThatThrownBy(() -> {
            voucherService.createVoucher(voucherDto);
        }).isInstanceOf(WrongRangeInputException.class);
    }

}