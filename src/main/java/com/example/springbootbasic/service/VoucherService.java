package com.example.springbootbasic.service;

import com.example.springbootbasic.VoucherConsoleApplication;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherEnum;
import com.example.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.springbootbasic.domain.voucher.VoucherEnum.PERCENT_DISCOUNT_VOUCHER;
import static com.example.springbootbasic.domain.voucher.VoucherEnum.generateVoucher;
import static com.example.springbootbasic.util.CharacterUnit.*;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherConsoleApplication.class);
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Long saveVoucher(VoucherEnum voucherType, Long amount) {
        Long newVoucherId = voucherRepository.getSequence();
        Voucher generatedVoucher = generateVoucher(newVoucherId, amount, voucherType);
        voucherRepository.save(newVoucherId, generatedVoucher);
        logger.debug("[{}] save VoucherType => '{}', DiscountValue => '{}'",
                this.getClass().getName(),
                voucherType.getVoucherType(),
                amount);
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
