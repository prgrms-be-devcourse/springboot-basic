package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(VoucherDto.Request request) {
        DiscountType discountType = request.discountType();
        int amount = request.discountAmount();
        Voucher voucher = discountType.createVoucher(amount);
        log.info("Create Voucher! DiscountType: {}, DiscountAmount: {}", voucher.getClass().getSimpleName(), request.discountAmount());
        return voucherRepository.save(voucher);
    }

    public VoucherDto.Response getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return VoucherDto.Response.toDto(vouchers);
    }
}
