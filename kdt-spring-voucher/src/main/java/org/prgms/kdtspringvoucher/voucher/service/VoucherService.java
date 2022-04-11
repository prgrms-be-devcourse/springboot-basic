package org.prgms.kdtspringvoucher.voucher.service;

import org.prgms.kdtspringvoucher.voucher.domain.*;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public void showAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();

        if (vouchers.isEmpty()) {
            logger.error("Have not any vouchers in list => {}", vouchers);
            System.out.println("No vouchers.....\n");
        }
        else {
            logger.info("Succeed show all vouchers in repository");
            vouchers.forEach(System.out::println);
        }
    }


    public Voucher createVoucher(VoucherType voucherType,Long amountOrPercent) {

        switch (voucherType) {
            case FIXED -> {
                return voucherRepository.save(createFixedAmountVoucher(amountOrPercent));
            }
            case PERCENT -> {
                return voucherRepository.save(createPercentDiscountVoucher(amountOrPercent));
            }
        }

        return null;
    }

    private Voucher createPercentDiscountVoucher(Long percent) {
        logger.info("Create percentDiscountVoucher");
        return new PercentDiscountVoucher(UUID.randomUUID(), percent);
    }

    private Voucher createFixedAmountVoucher(Long amount) {
        logger.info("Create fixedAmountVoucher");
        return new FixedAmountVoucher(UUID.randomUUID(), amount);
    }
}
