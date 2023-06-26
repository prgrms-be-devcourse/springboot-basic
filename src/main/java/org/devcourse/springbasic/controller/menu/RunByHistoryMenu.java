package org.devcourse.springbasic.controller.menu;
import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.Voucher;
import org.devcourse.springbasic.service.VoucherService;

import java.util.List;

public class RunByHistoryMenu implements RunByMenu {

    private final IODevice ioDevice;
    private final VoucherService voucherService;

    public RunByHistoryMenu(IODevice ioDevice, VoucherService voucherService) {
        this.ioDevice = ioDevice;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        List<Voucher> vouchers = voucherService.findAll();
        ioDevice.outputVouchers(vouchers);
    }
}
