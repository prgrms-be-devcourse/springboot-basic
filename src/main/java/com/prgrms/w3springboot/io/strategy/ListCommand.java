package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Console;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class ListCommand implements CommandStrategy {
    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        console.printVoucherList(voucherService.listVoucher());
        return true;
    }
}
