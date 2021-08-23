package org.programmers.voucher.service;

import org.programmers.voucher.model.FixedAmountVoucher;
import org.programmers.voucher.model.PercentDiscountVoucher;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.programmers.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final int LOWER_LIMIT = 0;
    private final int UPPER_LIMIT = 100;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAllVouchers();
    }

    public void createVoucher(UUID voucherId, VoucherType voucherType, long value) {

        switch (voucherType) {
            case FIXED -> {
                if (value < LOWER_LIMIT) {
                    System.out.println("You typed wrong number of voucher");
                } else {
                    voucherRepository.save(new FixedAmountVoucher(voucherId, value));
                }
                break;
            }
            case PERCENT -> {
                if (value <= LOWER_LIMIT || value >= UPPER_LIMIT) {
                    System.out.println("You typed wrong number of voucher");
                    break;
                } else {
                    voucherRepository.save(new PercentDiscountVoucher(voucherId, value));
                }
                break;
            }
            default -> {
                System.out.println("You typed wrong type of voucher");
            }
        }
    }

    public void memoryShowVoucherList() {
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();
        for (Voucher voucher : allVouchers) {
            System.out.println(("VoucherId : " + voucher.getVoucherId() + "\n" + "Voucher : " + voucher));
        }
    }

}
