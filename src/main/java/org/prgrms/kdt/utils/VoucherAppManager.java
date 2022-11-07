package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.io.MessageType;
import org.springframework.stereotype.Component;

@Component
public class VoucherAppManager {

    private final Power power;

    public VoucherAppManager() {
        this.power = new Power();
    }

    public boolean getPower() {
        return power.getIsRunning();
    }

    public void execute(SelectType selectType) {
        switch (selectType) {
            case CREATE -> {

            }
            case LIST -> {

            }
            case EXIT -> power.stop();
            default -> throw new WrongSelectException(MessageType.SELECT_WRONG.getMessage());
        }
    }
}
