package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.commendLine.Console;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.util.ErrorMessage;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void create(String inputType) {
        VoucherType voucherType = VoucherType.getType(inputType);
        voucherService.createVoucher(voucherType);
    }

    public List<Voucher> findAll() {
        return voucherService.findAll();
    }
}
