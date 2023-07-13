package com.prgms.VoucherApp.domain.voucher.model.factory;

import com.prgms.VoucherApp.domain.voucher.model.strategy.*;
import com.prgms.VoucherApp.view.VoucherCommand;

public final class VoucherCommandStrategyFactory {

    private VoucherCommandStrategyFactory() {

    }

    public static VoucherCommandStrategy from(VoucherCommand voucherCommand) {
        VoucherCommandStrategy voucherCommandStrategy = switch (voucherCommand) {
            case CREATE -> new VoucherCreate();
            case FIND_ALL -> new VoucherFindAll();
            case FIND_ONE -> new VoucherFindOne();
            case FIND_BY_VOUCHER_TYPE -> new VoucherFindByType();
            case UPDATE -> new VoucherUpdate();
            case DELETE -> new VoucherDelete();
            case EXIT -> new VoucherExit();
        };

        return voucherCommandStrategy;
    }
}
