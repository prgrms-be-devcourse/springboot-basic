package org.prgrms.kdt.app;

import org.springframework.stereotype.Component;


@Component
public class VoucherControllerStatus {
    private Boolean status = true;

    public Boolean isRunning() {
        return status;
    }

    public void quitProgram() {
        this.status = false;
    }
}
