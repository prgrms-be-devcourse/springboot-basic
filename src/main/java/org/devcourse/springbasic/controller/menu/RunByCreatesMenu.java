package org.devcourse.springbasic.controller.menu;

import org.devcourse.springbasic.io.IODevice;
import org.devcourse.springbasic.voucher.*;
import org.devcourse.springbasic.service.VoucherService;
import java.util.UUID;

public class RunByCreatesMenu implements RunByMenu {

    private final IODevice ioDevice;
    private final VoucherService voucherService;

    public RunByCreatesMenu(IODevice ioDevice, VoucherService voucherService) {
        this.ioDevice = ioDevice;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        ioDevice.outputVoucherMenus();
        VoucherType voucherType = ioDevice.inputVoucherMenu();
        Voucher voucher = voucherType.getFunctionToSelectVoucher().apply(UUID.randomUUID(), 10L);
        voucherService.save(voucher);
        ioDevice.outputVoucher(voucher);
    }
}

