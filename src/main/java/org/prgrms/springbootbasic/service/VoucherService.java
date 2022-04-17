package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.VoucherType;
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

    public void createVoucher(VoucherType voucherType, int amount, int percent) {
        logger.info("createVoucher() called");

        if (voucherType.isFixed()) {
            voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), amount));
        }
        if (voucherType.isPercent()) {
            voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), percent));
        }
    }

    public List<Voucher> findAll() {
        logger.info("VoucherService.findAll called");

        return voucherRepository.findAll();
    }
}
