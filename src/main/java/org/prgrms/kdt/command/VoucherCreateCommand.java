package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherData;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

/**
 * Created by yhh1056
 * Date: 2021/08/28 Time: 1:35 오후
 */

@Component
public class VoucherCreateCommand implements CommandOperator {

    private final VoucherService voucherService;

    public VoucherCreateCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operate(Console console) {
        VoucherData voucherData = console.inputVoucher();
        VoucherType voucherType = VoucherType.findByNumber(voucherData.getVoucherNumber());
        Voucher voucher = voucherType.create(voucherData);
        voucherService.createVoucher(voucher);
        console.successCreate();
    }
}
