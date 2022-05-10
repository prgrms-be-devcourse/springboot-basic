package org.devcourse.voucher.voucher.controller;

import org.devcourse.voucher.customer.service.BlacklistService;
import org.devcourse.voucher.menu.model.ListMenuType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ConsoleVoucherController implements VoucherController{
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;
    private final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);

    public ConsoleVoucherController(VoucherService voucherService, BlacklistService blacklistService) {
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
    }

    @Override
    public Voucher createVoucherMapper(VoucherType voucherType, long discount) {
        logger.info("Controller : Processing voucher generation");
        return voucherService.createVoucher(voucherType, discount);
    }

    @Override
    public List<?> findListMapper(ListMenuType listType) {
        List<?> list;

        logger.info("Controller : List inquire processing");
        switch(listType) {
            case VOUCHER -> list = voucherService.recallAllVoucher();
            case BLACKLIST -> list = blacklistService.recallAllBlacklist();
            default -> list = null;
        }
        return list;
    }
}

