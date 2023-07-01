package com.programmers.vouchermanagement.voucher.application;

import com.programmers.vouchermanagement.voucher.domain.*;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void createVoucher(VoucherDto.Request request) {
        DiscountType discountType = request.discountType();
        int discountAmount = request.discountAmount();
        Voucher voucher = toEntity(discountType, discountAmount);
        voucherRepository.save(voucher);
        log.info("Create Voucher! DiscountType: {}, DiscountAmount: {}", voucher.getClass().getSimpleName(), discountAmount);
    }

    public VoucherDto.Response getVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return toDto(vouchers);
    }

    private static Voucher toEntity(DiscountType discountType, int discountAmount) {
        if (DiscountType.FIX == discountType) {
            return new FixedAmountVoucher(discountAmount);
        }
        return new PercentDiscountVoucher(discountAmount);
    }

    private VoucherDto.Response toDto(List<Voucher> vouchers) {
        List<String> vourcherList = vouchers.stream()
                .map(voucher -> voucher.getClass().getSimpleName())
                .collect(Collectors.toList());
        return new VoucherDto.Response(vourcherList);
    }
}
