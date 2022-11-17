package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.service.VoucherService;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public void makeVoucher(String inputDiscountWay) {
        String discountWay = VoucherVariable.chooseDiscountWay(inputDiscountWay);
        voucherService.makeVoucher(discountWay);
    }


}
