package org.devcourse.springbasic.domain.voucher.service;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("요청을 통해 바우처를 저장할 수 있다.")
    public void testSave() {
        VoucherDto.SaveRequestDto saveRequestDto = new VoucherDto.SaveRequestDto(VoucherType.FIXED_AMOUNT_VOUCHER);
        voucherService.save(saveRequestDto);
    }

    @Test
    @DisplayName("(ResultSet에서 값을 추출해서) Voucher 객체를 만들 수 있다.")
    public void testRowMapper() {
        //== given  ==//
        UUID voucherId = UUID.randomUUID();
        Long discountAmount = 50L;

        //== when ==//
        Voucher voucher = VoucherType.PERCENT_DISCOUNT_VOUCHER.getVoucherBiFunction().apply(voucherId, discountAmount);

        //== then ==//
        assertThat(voucher.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher.getDiscountRate()).isEqualTo(discountAmount);
    }

    @Test
    @DisplayName("Id를 통해 바우처를 조회할 수 있다.")
    public void testFindById() {
        //== given ==//
        VoucherDto.SaveRequestDto fixedVoucherSaveRequestDto = new VoucherDto.SaveRequestDto(VoucherType.FIXED_AMOUNT_VOUCHER);
        VoucherDto.SaveRequestDto percentVoucherSaveRequestDto = new VoucherDto.SaveRequestDto(VoucherType.PERCENT_DISCOUNT_VOUCHER);
        UUID fixedVoucherId = voucherService.save(fixedVoucherSaveRequestDto);
        UUID percentVoucherId = voucherService.save(percentVoucherSaveRequestDto);

        //== when ==//
        VoucherDto.ResponseDto fixedVoucherResponseDto = voucherService.findById(fixedVoucherId);
        VoucherDto.ResponseDto percentVoucherResponseDto = voucherService.findById(percentVoucherId);

        //== then ==//
        assertThat(fixedVoucherResponseDto.getVoucherType()).isEqualTo(fixedVoucherResponseDto.getVoucherType());
        assertThat(fixedVoucherResponseDto.getDiscountRate()).isEqualTo(fixedVoucherResponseDto.getDiscountRate());
        assertThat(percentVoucherResponseDto.getVoucherType()).isEqualTo(percentVoucherResponseDto.getVoucherType());
        assertThat(percentVoucherResponseDto.getDiscountRate() ).isEqualTo(percentVoucherResponseDto.getDiscountRate());
    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    public void testFindAll() {
        //== given ==//
        VoucherDto.SaveRequestDto fixedVoucherSaveRequestDto = new VoucherDto.SaveRequestDto(VoucherType.FIXED_AMOUNT_VOUCHER);
        VoucherDto.SaveRequestDto percentVoucherSaveRequestDto = new VoucherDto.SaveRequestDto(VoucherType.PERCENT_DISCOUNT_VOUCHER);
        UUID fixedVoucherId = voucherService.save(fixedVoucherSaveRequestDto);
        UUID percentVoucherId = voucherService.save(percentVoucherSaveRequestDto);
        List<UUID> expectVoucherIds = new ArrayList<>(List.of(fixedVoucherId, percentVoucherId));

        //== when ==//
        List<VoucherDto.ResponseDto> Vouchers = voucherService.findAll();
        List<UUID> actualVoucherIds = Vouchers.stream().map(VoucherDto.ResponseDto::getVoucherId).collect(Collectors.toList());

        //== then ==//
        Collections.sort(expectVoucherIds);
        Collections.sort(actualVoucherIds);

        assertThat(expectVoucherIds).usingRecursiveComparison()
                .isEqualTo(actualVoucherIds);
    }
}