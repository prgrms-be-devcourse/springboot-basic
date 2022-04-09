package org.programmers.kdtspring.service;

import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(long amount) {
        logger.info("[VoucherService] createFixedAmountVoucher(long amount) called");

        Voucher FixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        voucherRepository.save(FixedAmountVoucher);

        logger.info("{} saved", FixedAmountVoucher);
    }

    public void createPercentDiscountVoucher(long percent) {
        logger.info("[VoucherService] createPercentDiscountVoucher(long amount) called");

        Voucher PercentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.save(PercentDiscountVoucher);

        logger.info("{} saved", PercentDiscountVoucher);
    }

}
