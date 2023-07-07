package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.DiscountPolicy;
import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(VoucherDto request) {
        DiscountType discountType = request.discountType();
        int amount = request.amount();
        DiscountPolicy discountPolicy = discountType.createDiscountPolicy(amount);
        Voucher voucher = new Voucher(discountPolicy);
        log.info("Create Voucher! DiscountType: {}, DiscountAmount: {}", voucher.getClass().getSimpleName(), request.amount());
        return voucherRepository.save(voucher);
    }

    public List<VoucherDto> getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherDto::toDto)
                .toList();
    }
}
