package com.prgrms.presentation.command;

import com.prgrms.presentation.view.ViewManager;
import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements Command {

    @Override
    public Power execute(ViewManager viewManager) {
        viewManager.guideClose();

        return Power.OFF;
    }
}
