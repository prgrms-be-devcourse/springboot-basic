package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.ViewManager;
import com.prgrms.model.voucher.dto.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCommand implements Command {
    private VoucherController voucherController;
    private ViewManager viewManager;

    public ListCommand(VoucherController voucherController, ViewManager viewManager) {
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }

    @Override
    public Power execute() {
        List<VoucherResponse> vouchers = voucherController.listVoucher();
        viewManager.viewVoucherList(vouchers);

        return Power.ON;
    }
}
