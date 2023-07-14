package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.voucher.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import com.prgms.voucher.voucherproject.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> list() {
        return voucherRepository.findAll();
    }

    public void create(VoucherType voucherType, long discount) {
        Voucher voucher = switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(discount);
            case PERCENT -> new PercentDiscountVoucher(discount);
        };

        try {
            voucherRepository.save(voucher);
        } catch (IllegalArgumentException e) {
            logger.error("{} IllegalArgumentException -> {}", voucherType, discount);
        }
    }

}
