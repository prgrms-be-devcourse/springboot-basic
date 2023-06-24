package org.weekly.weekly.voucher;

import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.service.VoucherService;

public class VoucherController {
    private final VoucherService voucherService;
    private final CommandLineApplication commandLineApplication;

    public VoucherController(VoucherService voucherService, CommandLineApplication commandLineApplication) {
        this.voucherService = voucherService;
        this.commandLineApplication = commandLineApplication;
    }

    public void start() {
        boolean isStop = false;

        while(!isStop) {
            isStop = selectMenu(this.commandLineApplication.readMenu());
        }
    }

    private boolean selectMenu(VoucherMenu voucherMenu) {
        if (VoucherMenu.CREATE.equals(voucherMenu)) {
            createVoucher();
            return false;
        }

        if (VoucherMenu.LIST.equals(voucherMenu)) {
            getList();
            return true;
        }

        return false;
    }

    private void createVoucher() {
        VoucherDto voucherDto = this.commandLineApplication.readVoucher();
    }

    private void getList() {

    }




}
