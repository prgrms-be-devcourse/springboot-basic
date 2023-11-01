package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
//@Sql("/truncate.sql")
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Test
    void voucher_생성_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(10, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);

        // when
        VoucherResponseDto voucher = voucherService.createVoucher(voucherCreateRequestDto);

        // then
        Assertions.assertEquals(10, voucher.discount());
        Assertions.assertEquals(VoucherDiscountType.FIXED_AMOUNT_DISCOUNT, voucher.voucherDiscountType());
    }

    @Test
    void voucher_삭제_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        voucherService.deleteVoucher(voucher.id());

        // then
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        Assertions.assertTrue(voucherResponseDtos.isEmpty());
    }

    @Test
    void voucher_전체_리스트_조회(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher1 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher2 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher3 = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();

        // then
        Assertions.assertEquals(3, voucherResponseDtos.size());
    }
}
