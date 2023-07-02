package com.prgms.voucher.voucherproject.service;

import com.prgms.voucher.voucherproject.domain.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.Voucher;
import com.prgms.voucher.voucherproject.domain.VoucherType;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;
    private final Console console = new Console();
    private final String CREATE_FIXED_MSG = "고정 할인 금액을 입력하세요. (1이상)";
    private final String CREATE_PERCENT_MSG = "퍼센트 할인 금액을 입력하세요. (1~99)";
    private long discount;
    private long percent;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    private void createFixedVoucher() {
        console.printMsg(CREATE_FIXED_MSG);
        discount = console.inputDiscountAmount();
        console.bufferDeleted();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
        voucherRepository.save(fixedAmountVoucher);
    }

    private void createPercentVoucher() {
        console.printMsg(CREATE_PERCENT_MSG);
        percent = console.inputDiscountAmount();
        console.bufferDeleted();
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), percent);
        voucherRepository.save(percentDiscountVoucher);
    }

    public void list() {
        List<Voucher> voucherList = voucherRepository.findAll();

        if (voucherList.isEmpty()) {
            console.printNoVoucher();
        } else {
            voucherList.forEach(voucher -> console.printMsg(voucher.toString()));
        }
    }

    public void create(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> {
                try {
                    createFixedVoucher();
                } catch (IllegalArgumentException e) {
                    logger.error("FixedAmountVoucher IllegalArgumentException -> {}", discount);
                    System.out.println(e.getLocalizedMessage());
                }
            }


            case PERCENT -> {
                try {
                    createPercentVoucher();
                } catch (IllegalArgumentException e) {
                    logger.error("PercentDiscountVoucher IllegalArgumentException -> {}", percent);
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

}
