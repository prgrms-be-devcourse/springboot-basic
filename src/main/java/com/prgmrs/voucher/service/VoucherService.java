package com.prgmrs.voucher.service;

import com.prgmrs.voucher.dto.VoucherRequest;
import com.prgmrs.voucher.enums.ConsoleViewVoucherCreationEnum;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.DiscountValue;
import com.prgmrs.voucher.model.vo.Percent;
import com.prgmrs.voucher.repository.VoucherRepository;
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

    public UUID createVoucher(VoucherRequest voucherRequest) {
        ConsoleViewVoucherCreationEnum voucherType = voucherRequest.getConsoleViewVoucherCreationEnum();
        DiscountValue discountValue = voucherRequest.getValue();
        UUID uuid = UUID.randomUUID();
        Voucher voucher;
        switch (voucherType) {
            case CREATE_FIXED_AMOUNT_VOUCHER -> {
                Amount amount = new Amount(discountValue.getValue());
                voucher = new FixedAmountVoucher(uuid, amount);
            }
            case CREATE_PERCENT_DISCOUNT_VOUCHER ->  {
                Percent percent = new Percent(discountValue.getValue());
                voucher = new PercentDiscountVoucher(uuid, percent);
            }
            default -> {
                logger.error("unexpected error occurred: unexpected voucher type");
                throw new NoSuchVoucherTypeException("unexpected voucher type");
            }
        }
        voucherRepository.save(voucher);
        return uuid;
    }

    public Map<UUID, Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucherById(UUID uuid) {
        return voucherRepository.findVoucherById(uuid);
    }
}
