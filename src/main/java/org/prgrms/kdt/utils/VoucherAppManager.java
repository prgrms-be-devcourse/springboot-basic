package org.prgrms.kdt.utils;

import org.prgrms.kdt.exceptions.WrongSelectException;
import org.prgrms.kdt.voucher.VoucherProviderManager;
import org.springframework.stereotype.Component;

@Component
public class VoucherAppManager {

    private final Power power;
    private final VoucherProviderManager voucherProviderManager;

    public VoucherAppManager(VoucherProviderManager voucherCreateManager) {
        this.voucherProviderManager = voucherCreateManager;
        this.power = new Power();
    }

    public boolean getPower() {
        return power.getIsRunning();
    }

    public void execute(SelectType selectType) {
        switch (selectType) {
            case CREATE -> voucherProviderManager.create();
            case LIST -> voucherProviderManager.list();
            case EXIT -> power.stop();
            default -> throw new WrongSelectException(MessageType.SELECT_WRONG.getMessage());
        }
    }
}
