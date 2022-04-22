package org.prgms.kdtspringvoucher.voucher.service;

import org.prgms.kdtspringvoucher.voucher.domain.*;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> showAllVoucher() {
        List<Voucher> vouchers = voucherRepository.findAll();
        if (vouchers.isEmpty())
            System.out.println("No vouchers.....\n");
        else {
            vouchers.forEach(voucher -> System.out.println((vouchers.indexOf(voucher) + 1) + ". " + voucher));
        }
        return vouchers;
    }


    public Voucher createVoucher(VoucherType voucherType,Long amountOrPercent) {
        switch (voucherType) {
            case FIXED -> {
                return voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(),amountOrPercent,voucherType, LocalDateTime.now()));
            }
            case PERCENT -> {
                return voucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), amountOrPercent, voucherType, LocalDateTime.now()));
            }
        }
        return null;
    }
}
