package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoucherController {
    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public void makeVoucher(String discountWay) {
        if (discountWay.equals(VoucherVariable.PERCENT.toString())) {
            logger.info("make percent voucher");
            voucherService.makePercentVoucher();
            return;
        }

        if (discountWay.equals(VoucherVariable.FIXED.toString())) {
            logger.info("make fixed voucher");
            voucherService.makeFixedVoucher();
            return;
        }
        throw new IllegalArgumentException(discountWay + "은(는) 존재하지 않는 할인 방법 입니다.");
    }


}
