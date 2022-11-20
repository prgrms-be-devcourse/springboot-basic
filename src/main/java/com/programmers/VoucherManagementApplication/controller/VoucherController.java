package com.programmers.VoucherManagementApplication.controller;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;
import com.programmers.VoucherManagementApplication.service.VoucherService;
import com.programmers.VoucherManagementApplication.voucher.Voucher;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(VoucherType voucherType, Amount amount) {
        return voucherService.create(voucherType, amount);
    }

    public List<Voucher> findAll() {
        return voucherService.findAll();
    }
}
