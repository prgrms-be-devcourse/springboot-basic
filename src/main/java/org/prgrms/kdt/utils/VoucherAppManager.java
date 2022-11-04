package org.prgrms.kdt.utils;

import org.springframework.stereotype.Component;

@Component
public class VoucherAppManager {
    private boolean appStatus;

    public VoucherAppManager() {
        this.appStatus = true;
    }

    public boolean getAppStatus() {
        return appStatus;
    }

    public void changeAppStatusToStop() {
        appStatus = false;
    }
}
