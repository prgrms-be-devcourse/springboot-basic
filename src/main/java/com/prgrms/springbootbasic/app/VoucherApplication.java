package com.prgrms.springbootbasic.app;

import com.prgrms.springbootbasic.handler.menu.MenuHandler;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {
    private final MenuHandler menuHandler;

    public VoucherApplication(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }

    public void runLifecycle() {
        boolean readyToExit = false;
        while (!readyToExit) {
            readyToExit = menuHandler.handleBeforeExit();
        }
    }
}
