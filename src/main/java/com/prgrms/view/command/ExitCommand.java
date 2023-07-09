package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.ViewManager;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {
    private ViewManager viewManager;
    private VoucherController voucherController;

    public ExitCommand(VoucherController voucherController ,ViewManager viewManager) {
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }

    @Override
    public Power execute() {
        viewManager.guideClose();

        return Power.OFF;
    }
}
