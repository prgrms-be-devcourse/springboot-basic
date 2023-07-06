package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.service.VoucherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class VoucherController {

    private final VoucherService voucherService;

    public void createVoucher(VoucherType voucherType, String inputNumber) {
        voucherService.saveVoucher(voucherType, inputNumber);
    }

    public List<Voucher> getVoucherList() {
        return voucherService.findVoucherAll();
    }
}
