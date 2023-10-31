package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.Voucher;
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
    void vocher_생성_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);

        // when
        Voucher voucher = voucherService.createVoucher(voucherCreateRequestDto);

        // then
        Assertions.assertEquals(10, voucher.getDiscount());
        Assertions.assertEquals(VoucherDiscountType.PERCENT_DISCOUNT, voucher.getVoucherDiscountType());
    }

    @Test
    void vocher_삭제_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        Voucher voucher = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        voucherService.deleteVoucher(voucher.getId());

        // then
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        Assertions.assertTrue(voucherResponseDtos.isEmpty());
    }

    @Test
    void vocher_전체_리스트_조회(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        Voucher voucher1 = voucherService.createVoucher(voucherCreateRequestDto);
        Voucher voucher2 = voucherService.createVoucher(voucherCreateRequestDto);
        Voucher voucher3 = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();

        // then
        Assertions.assertEquals(3, voucherResponseDtos.size());
    }
}
