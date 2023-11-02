package com.pgms.part1.domain.voucher.service;

import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherWebCreateRequestDto;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.exception.VoucherApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Test
    void voucher_오늘_날짜검색_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher1 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher2 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher3 = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        List<VoucherResponseDto> vouchersByCreatedDate = voucherService.findVouchersByCreatedDateAndType(LocalDate.now().toString(), null);

        // then
        Assertions.assertEquals(3, vouchersByCreatedDate.size());
    }

    @Test
    void voucher_어제_날짜검색_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher1 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher2 = voucherService.createVoucher(voucherCreateRequestDto);
        VoucherResponseDto voucher3 = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        List<VoucherResponseDto> vouchersByCreatedDate = voucherService.findVouchersByCreatedDateAndType(LocalDate.now().minusDays(1).toString(), null);

        // then
        Assertions.assertEquals(0, vouchersByCreatedDate.size());
    }

    @Test
    void voucher_날짜검색_잘못된양식_실패(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher1 = voucherService.createVoucher(voucherCreateRequestDto);

        // expected
        Assertions.assertThrows(VoucherApplicationException.class, () -> voucherService.findVouchersByCreatedDateAndType("testtest1234", null));
    }

    @Test
    void voucher_타입검색_성공(){
        // given
        VoucherWebCreateRequestDto voucherCreateRequestDto = new VoucherWebCreateRequestDto(1, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);
        VoucherResponseDto voucher1 = voucherService.createVoucher(voucherCreateRequestDto);

        // when
        List<VoucherResponseDto> vouchersByCreatedDateAndType = voucherService.findVouchersByCreatedDateAndType(null, VoucherDiscountType.FIXED_AMOUNT_DISCOUNT);

        // then
        Assertions.assertEquals(1, vouchersByCreatedDateAndType.size());
    }
}
