package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.controller.WalletController;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ControllerManager {
    private final Map<Integer, ApplicationController> controllerMap = new HashMap<>();

    @Autowired
    public ControllerManager(CustomerController customerController,
                             VoucherController voucherController,
                             WalletController walletController) {
        controllerMap.put(1, customerController);
        controllerMap.put(2, voucherController);
        controllerMap.put(3, walletController);
    }

    public ApplicationController getController(int modeNumber) {
        return Optional.ofNullable(controllerMap.get(modeNumber))
                .orElseThrow(() -> new NotFoundException("Controller not found for mode number: " + modeNumber));
    }

    public boolean isExit(int modeNumber) {
        return modeNumber == 0;
    }
}
