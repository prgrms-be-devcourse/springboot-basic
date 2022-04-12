package org.prgrms.kdt.controller;

import org.prgrms.kdt.models.Voucher;
import org.prgrms.kdt.service.VoucherService;

import java.util.List;

public class Controller {

    VoucherService voucherService;

    public Controller(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public List<Voucher> findAll() {
        return null;
    }

    public Voucher create(long value, String type) {
        return null;
    }
}
