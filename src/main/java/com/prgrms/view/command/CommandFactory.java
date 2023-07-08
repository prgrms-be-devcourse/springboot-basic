package com.prgrms.view.command;

import com.prgrms.controller.VoucherController;
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

    public Command createExitCommand() {
        return new ExitCommand(viewManager);
    }

    public Command createCreateCommand() {
        return new CreateCommand(voucherController, viewManager);
    }

    public Command createListCommand() {
        return new ListCommand(voucherController, viewManager);
    }

}
