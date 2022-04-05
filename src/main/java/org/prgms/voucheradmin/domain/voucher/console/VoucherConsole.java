package org.prgms.voucheradmin.domain.voucher.console;

import org.prgms.voucheradmin.domain.voucher.service.VoucherService;

public class VoucherConsole {
    private final VoucherService voucherService;

    public VoucherConsole(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
}
