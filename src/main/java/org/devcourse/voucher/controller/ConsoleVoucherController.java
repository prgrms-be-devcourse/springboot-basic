package org.devcourse.voucher.controller;

import org.devcourse.voucher.model.ListType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class ConsoleVoucherController implements VoucherController{
    private final VoucherService voucherService;

    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Voucher createVoucherMapper(VoucherType voucherType, long discount) {
        Voucher voucher = voucherService.createVoucher(voucherType, discount);
        return voucher;
    }

    @Override
    public List<Object> findListMapper(ListType list) {
        return null;
    }
}
