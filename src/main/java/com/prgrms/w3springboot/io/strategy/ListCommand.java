package com.prgrms.w3springboot.io.strategy;

import com.prgrms.w3springboot.io.Input;
import com.prgrms.w3springboot.io.Output;
import com.prgrms.w3springboot.voucher.service.VoucherService;

public class ListCommand implements CommandStrategy {
    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        output.printVoucherList(voucherService.listVoucher());
        return true;
    }
}
