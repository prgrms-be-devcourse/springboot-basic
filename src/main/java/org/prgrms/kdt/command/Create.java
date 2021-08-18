package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 2:29 오전
 */
public class Create implements Command {

    private Voucher voucher;
    private final VoucherService voucherService;

    public Create(Voucher voucher, VoucherService voucherService) {
        this.voucher = voucher;
        this.voucherService = voucherService;
    }

    @Override
    public void execute() {
        voucherService.createVoucher(voucher);
    }
}
