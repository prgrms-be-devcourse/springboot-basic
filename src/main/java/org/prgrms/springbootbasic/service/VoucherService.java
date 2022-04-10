package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void createFixedAmountVoucher(long amount) {
        logger.info("VoucherService.createFixedAmountVoucher() called");

        voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
    }

    public void createPercentAmountVoucher(int percent) {
        logger.info("VoucherService.createPercentAmountVoucher called");

        voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), percent));
    }

    public List<Voucher> findAll() {
        logger.info("VoucherService.findAll called");

        return voucherRepository.findAll();
    }
}
