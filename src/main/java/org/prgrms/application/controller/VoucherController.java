package org.prgrms.application.controller;

import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.domain.voucher.VoucherTypeFactory;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private final VoucherTypeFactory voucherTypeFactory;
    private VoucherService voucherService;

    public VoucherController(VoucherTypeFactory voucherTypeFactory) {
        this.voucherTypeFactory = voucherTypeFactory;
    }

    public List<Voucher> getStorage() {
        return voucherService.getVoucherList();
    }

    public void createVoucher(VoucherType voucherType, double discountAmount) {
        voucherService = voucherTypeFactory.getType(voucherType);
        voucherService.createVoucher(voucherType, discountAmount);
    }
}