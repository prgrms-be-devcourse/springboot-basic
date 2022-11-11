package org.prgrms.kdt.manager;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.SelectType;
import org.springframework.stereotype.Component;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

@Component
public class VoucherAppManager {

    private final Power power;
    private final VoucherProviderManager voucherProviderManager;

    public VoucherAppManager(VoucherProviderManager voucherCreateManager) {
        this.voucherProviderManager = voucherCreateManager;
        this.power = new Power();
    }

    public boolean isRunning() {
        return power.getIsRunning();
    }

    public void execute(SelectType selectType) {
        switch (selectType) {
            case CREATE -> {
                voucherProviderManager.create();
            }
            case LIST -> {
                voucherProviderManager.list();
            }
            case EXIT -> {
                power.stop();
            }
            default -> {
                throw new WrongSelectException(getSelectWrongMessage());
            }
        }
    }
}
