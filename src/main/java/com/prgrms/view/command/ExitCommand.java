package com.prgrms.view.command;

import com.prgrms.view.ViewManager;

public class ExitCommand implements Command {
    private ViewManager viewManager;

    public ExitCommand(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public Power execute() {
        viewManager.guideClose();

        return Power.OFF;
    }
}
