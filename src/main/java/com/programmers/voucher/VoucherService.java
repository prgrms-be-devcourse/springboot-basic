package com.programmers.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(long discountAmount) {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
        logger.info("FixedAmountVoucher 가 생성되었습니다.");
        voucherRepository.insert(voucher);
    }

    public void createPercentDiscountVoucher(long discountPercent) {
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
        logger.info("PercentDiscountVoucher 가 생성되었습니다.");
        voucherRepository.insert(voucher);
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }
}
