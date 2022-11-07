package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.io.MessageType;
import org.springframework.stereotype.Component;

@Component
public class VoucherAppManager {

    private Power power;

    public VoucherAppManager() {
        this.power = new Power();
    }

    public boolean getPower() {
        return power.getIsRunning();
    }


}
