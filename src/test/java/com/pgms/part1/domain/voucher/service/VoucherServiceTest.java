package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@Sql("/truncate.sql")
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @Test
    void vocher_생성_성공(){
        // given
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(1, 10);

        // when
        Voucher voucher = voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);

        // then
        Assertions.assertEquals(10, voucher.getDiscount());
        Assertions.assertEquals(VoucherDiscountType.PERCENT_DISCOUNT, voucher.getVoucherDiscountType());
    }

    @Test
    void vocher_삭제_성공(){
        // given
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(1, 10);
        Voucher voucher = voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);

        // when
        voucherService.deleteVoucher(voucher.getId());

        // then
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();
        Assertions.assertTrue(voucherResponseDtos.isEmpty());
    }

    @Test
    void vocher_전체_리스트_조회(){
        // given
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(1, 10);
        Voucher voucher1 = voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);
        Voucher voucher2 = voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);
        Voucher voucher3 = voucherService.createVoucher(voucherCreateRequestDto, VoucherDiscountType.PERCENT_DISCOUNT);

        // when
        List<VoucherResponseDto> voucherResponseDtos = voucherService.listVoucher();

        // then
        Assertions.assertEquals(3, voucherResponseDtos.size());
    }
}
