package com.prgmrs.voucher.service;

import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.repository.VoucherRepository;
import com.prgmrs.voucher.view.ConsoleViewVoucherCreationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(long value, ConsoleViewVoucherCreationEnum type) {
        Voucher voucher;
        UUID uuid = UUID.randomUUID();
        if (ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER == type) {
            voucher = new FixedAmountVoucher(uuid, value);
            voucherRepository.save(voucher);
            return uuid;
        }

        if (ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER == type) {
            voucher = new PercentDiscountVoucher(uuid, value);
            voucherRepository.save(voucher);
            return uuid;
        }

        RuntimeException re = new RuntimeException("unexpected voucher type");
        logger.error("unexpected error occurred: ", re);
        throw re;
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucherById(UUID uuid) {
        return voucherRepository.findVoucherById(uuid);
    }
}
