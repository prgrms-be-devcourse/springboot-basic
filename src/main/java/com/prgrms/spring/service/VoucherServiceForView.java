package com.prgrms.spring.service;


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

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VoucherServiceForView {

    private final VoucherRepository voucherRepository;

    @Transactional(readOnly = true)
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    @Transactional
    public void createVoucher(VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (VoucherType.valueOf(requestDto.getVoucherType()) == VoucherType.FIXED_AMOUNT) {
            voucher = FixedAmountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        } else if (VoucherType.valueOf(requestDto.getVoucherType()) == VoucherType.PERCENT_DISCOUNT) {
            voucher = PercentDiscountVoucher.newInstance(UUID.randomUUID(), requestDto.getDiscount());
        }
        voucherRepository.insert(voucher);
    }

    @Transactional
    public void deleteVoucher(String voucherId) {
        Voucher voucher = voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_VOUCHER_EXCEPTION, Error.NOT_FOUND_VOUCHER_EXCEPTION.getMessage()));
        voucherRepository.deleteVoucher(voucher);
    }

    @Transactional(readOnly = true)
    public Voucher getVoucherById(String voucherId) {
        return voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_VOUCHER_EXCEPTION, Error.NOT_FOUND_VOUCHER_EXCEPTION.getMessage()));
    }
}
