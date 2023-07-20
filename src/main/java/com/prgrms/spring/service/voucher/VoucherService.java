package com.prgrms.spring.service.voucher;

import com.prgrms.spring.controller.dto.request.VoucherCreateRequestDto;
import com.prgrms.spring.controller.dto.response.VoucherResponseDto;
import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.model.NotFoundException;
import com.prgrms.spring.repository.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public VoucherResponseDto createVoucher(VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (VoucherType.valueOf(requestDto.getVoucherType()) == VoucherType.FIXED_AMOUNT) {
            voucher = FixedAmountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        } else if (VoucherType.valueOf(requestDto.getVoucherType()) == VoucherType.PERCENT_DISCOUNT) {
            voucher = PercentDiscountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        }
        voucherRepository.insert(voucher);
        return VoucherResponseDto.of(voucher.getVoucherName(), voucher.getDiscount() + voucher.getDiscountUnit());
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> getAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(voucher -> VoucherResponseDto.of(
                voucher.getVoucherName(),
                voucher.getDiscount() + voucher.getDiscountUnit()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> getSearchedVoucher(VoucherType voucherType) {
        String discountUnit = null;
        switch (voucherType) {
            case PERCENT_DISCOUNT:
                discountUnit = "%";
                break;
            case FIXED_AMOUNT:
                discountUnit = "$";
                break;
        }
        List<Voucher> vouchers = voucherRepository.findByDiscountUnit(discountUnit);
        return vouchers.stream().map(voucher -> VoucherResponseDto.of(
                voucher.getVoucherName(),
                voucher.getDiscount() + voucher.getDiscountUnit()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void deleteVoucher(String voucherId) {
        Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_VOUCHER_EXCEPTION, Error.NOT_FOUND_VOUCHER_EXCEPTION.getMessage()));
        voucherRepository.deleteVoucher(voucher);
    }

    @Transactional(readOnly = true)
    public VoucherResponseDto getVoucherById(String voucherId) {
        Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_VOUCHER_EXCEPTION, Error.NOT_FOUND_VOUCHER_EXCEPTION.getMessage()));
        return VoucherResponseDto.of(voucher.getVoucherName(), voucher.getDiscount() + voucher.getDiscountUnit());
    }

}
