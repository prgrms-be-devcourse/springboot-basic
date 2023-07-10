package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.Voucher;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import com.prgms.voucher.voucherproject.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;
    private final Console console = new Console();

    private long discount;
    private long percent;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    private void createFixedVoucher() {
        console.printMessage(Constant.CREATE_FIXED_VOUCHER, true);
        discount = console.inputDiscountAmount();
        console.bufferDeleted();
        try {
            Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
            voucherRepository.save(fixedAmountVoucher);
        } catch (IllegalArgumentException e) {
            exceptionCatch("fixedAmountVoucher", e);
        }

    }

    private void createPercentVoucher() {
        console.printMessage(Constant.CREATE_PERCENT_VOUCHER, true);
        percent = console.inputDiscountAmount();
        console.bufferDeleted();
        try {
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
            voucherRepository.save(percentDiscountVoucher);
        } catch (IllegalArgumentException e) {
            exceptionCatch("percentDiscountVoucher", e);
        }

    }

    public void list() {
        List<Voucher> voucherList = voucherRepository.findAll();

        if (voucherList.isEmpty()) {
            console.printNoVoucher();
        }

        for (Voucher v : voucherList) {
            console.printVoucherInfo(v);
        }

    }

    public void create(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> {
                createFixedVoucher();
            }

            case PERCENT -> {
                createPercentVoucher();
            }
        }
    }

    private void exceptionCatch(String voucherType, Exception e) {
        logger.error("{} IllegalArgumentException -> {}", voucherType, discount);
        System.out.println(e.getLocalizedMessage());
    }

}
