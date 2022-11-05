package com.example.springbootbasic.service;

import com.example.springbootbasic.repository.VoucherRepository;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springbootbasic.domain.voucher.VoucherEnum.*;
import static com.example.springbootbasic.util.CharacterUnit.*;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Long saveVoucher(VoucherEnum voucherType, Long amount) {
        Long newVoucherId = voucherRepository.getSequence();
        Voucher generatedVoucher = generateVoucher(newVoucherId, amount, voucherType);
        voucherRepository.save(newVoucherId, generatedVoucher);
        return newVoucherId;
    }

    public String findAllVouchers() {
        return buildResponseBodyBy(voucherRepository.findAllVouchers());
    }

    private String buildResponseBodyBy(List<Voucher> findAllVouchers) {
        StringBuilder responseBodyBuilder = new StringBuilder();
        findAllVouchers.forEach(voucher -> {
            responseBodyBuilder
                    .append(voucher.getVoucherEnum().getVoucherType())
                    .append(SPACE.getUnit())
                    .append(voucher.getDiscountValue());
            if (voucher.getVoucherEnum() == PERCENT_DISCOUNT_VOUCHER) {
                responseBodyBuilder.append(PERCENT.getUnit());
            }
            responseBodyBuilder.append(ENTER.getUnit());
        });

        return responseBodyBuilder.toString();
    }
}
