package org.prgrms.kdt.utils;

import org.springframework.stereotype.Component;


@Component
public class VoucherControllerManager {
    private Boolean status = true;

    public Boolean isRunning() {
        return status;
    }

    public void quitProgram() {
        this.status = false;
    }
}
