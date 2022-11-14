package org.prgrms.kdt.manager;

import org.prgrms.kdt.exceptions.InvalidITypeInputException;
import org.prgrms.kdt.utils.Power;
import org.prgrms.kdt.utils.SelectType;
import org.springframework.stereotype.Component;

import static org.prgrms.kdt.io.IOManager.getSelectWrongMessage;

@Component
public class VoucherAppManager {

    private final Power power;
    private final VoucherProviderManager voucherProviderManager;

    public VoucherAppManager(VoucherProviderManager voucherProviderManager) {
        this.voucherProviderManager = voucherProviderManager;
        this.power = new Power();
    }

    public boolean isRunning() {
        return power.getIsRunning();
    }

    public void execute(SelectType selectType) {
        switch (selectType) {
            case CREATE -> {
                voucherProviderManager.runCreateCycle();
            }
            case LIST -> {
                voucherProviderManager.runGetList();
            }
            case EXIT -> {
                power.stop();
            }
            default -> {
                throw new InvalidITypeInputException(getSelectWrongMessage());
            }
        }
    }
}
