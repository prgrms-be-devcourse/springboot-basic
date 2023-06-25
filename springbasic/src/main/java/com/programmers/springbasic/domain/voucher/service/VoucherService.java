package com.programmers.springbasic.domain.voucher.service;

import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbasic.domain.voucher.repository.FixedAmountVoucherFileRepository;
import com.programmers.springbasic.domain.voucher.repository.PercentDiscountVoucherFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VoucherService {
    private final FixedAmountVoucherFileRepository fixedVoucherRepository;
    private final PercentDiscountVoucherFileRepository percentVoucherRepository;

    public void createFixedAmountVoucher(double fixedAmount) {
        FixedAmountVoucher voucher = new FixedAmountVoucher(fixedAmount);

        fixedVoucherRepository.save(voucher);
    }

    public void createPercentDiscountVoucher(double percentDiscount) {
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(percentDiscount);

        percentVoucherRepository.save(voucher);
    }

    public List<String> getAllFixedAmountVoucherInfo() {
        List<FixedAmountVoucher> fixedAmountVouchers = fixedVoucherRepository.findAll();

        return fixedAmountVouchers.stream()
                .map(v -> v.getInfo())
                .collect(Collectors.toList());
    }

    public List<String> getAllPercentDiscountVoucherInfo() {
        List<PercentDiscountVoucher> percentDiscountVouchers = percentVoucherRepository.findAll();

        return percentDiscountVouchers.stream()
                .map(v -> v.getInfo())
                .collect(Collectors.toList());
    }
}
