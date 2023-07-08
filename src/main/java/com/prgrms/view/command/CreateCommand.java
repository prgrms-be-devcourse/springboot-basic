package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.ViewManager;
import com.prgrms.model.voucher.dto.VoucherRequest;

public class CreateCommand implements Command{
    private VoucherController voucherController;
    private ViewManager viewManager;

    public CreateCommand (VoucherController voucherController, ViewManager viewManager){
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }
    @Override
    public Power execute() {
        VoucherRequest voucherRequest = viewManager.guideCreateVoucher();
        voucherController.createVoucher(voucherRequest);

        return Power.ON;
    }
}
