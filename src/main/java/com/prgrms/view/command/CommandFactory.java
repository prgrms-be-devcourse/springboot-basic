package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.Menu;
import com.prgrms.view.ViewManager;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {
    private VoucherController voucherController;
    private ViewManager viewManager;

    public CommandFactory(VoucherController voucherController, ViewManager viewManager) {
        this.voucherController = voucherController;
        this.viewManager = viewManager;
    }

    public Command createCommand(Menu menu) {
        return menu.createCommand(voucherController, viewManager);
    }


}
