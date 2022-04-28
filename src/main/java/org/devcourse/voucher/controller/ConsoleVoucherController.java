package org.devcourse.voucher.controller;

import org.devcourse.voucher.customer.service.BlacklistService;
import org.devcourse.voucher.model.ListType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ConsoleVoucherController implements VoucherController{
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    public ConsoleVoucherController(VoucherService voucherService, BlacklistService blacklistService) {
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
    }

    @Override
    public Voucher createVoucherMapper(VoucherType voucherType, long discount) {
        return voucherService.createVoucher(voucherType, discount);
    }

    @Override
    public List<?> findListMapper(ListType listType) {
        List<?> list;
        switch(listType) {
            case VOUCHER -> list = voucherService.recallAllVoucher();
            case BLACKLIST -> list = blacklistService.recallAllBlacklist();
            default -> list = null;
        }
        return list;
    }
}

