package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.VoucherService;
import org.springframework.stereotype.Component;

/**
 * Created by yhh1056
 * Date: 2021/08/28 Time: 1:42 오후
 */

@Component
public class VoucherListCommand implements CommandOperator {

    private final VoucherService voucherService;

    public VoucherListCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operate(Console console) {
        console.printVouchers(voucherService.getAllVoucher());
        console.printNextCommand();
    }
}
