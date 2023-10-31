package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.console.ConsoleIOManager;
import org.prgrms.prgrmsspring.console.CustomerConsole;
import org.prgrms.prgrmsspring.console.VoucherConsole;
import org.prgrms.prgrmsspring.console.WalletConsole;
import org.prgrms.prgrmsspring.controller.console.ApplicationController;
import org.prgrms.prgrmsspring.controller.console.CustomerController;
import org.prgrms.prgrmsspring.controller.console.VoucherController;
import org.prgrms.prgrmsspring.controller.console.WalletController;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ControllerManager {
    private final Map<Integer, ApplicationController> controllerMap = new HashMap<>();
    private final Map<ApplicationController, ConsoleIOManager> consoleManagerMap = new HashMap<>();

    @Autowired
    public ControllerManager(CustomerController customerController,
                             VoucherController voucherController,
                             WalletController walletController,
                             CustomerConsole customerConsole,
                             VoucherConsole voucherConsole,
                             WalletConsole walletConsole) {
        controllerMap.put(1, customerController);
        controllerMap.put(2, voucherController);
        controllerMap.put(3, walletController);
        consoleManagerMap.put(customerController, customerConsole);
        consoleManagerMap.put(voucherController, voucherConsole);
        consoleManagerMap.put(walletController, walletConsole);
    }

    public ApplicationController getController(int modeNumber) {
        return Optional.ofNullable(controllerMap.get(modeNumber))
                .orElseThrow(() -> new NotFoundException("Controller not found for mode number: " + modeNumber));
    }


    public ConsoleIOManager getConsoleIoManager(ApplicationController controller) {
        return consoleManagerMap.get(controller);
    }

    public boolean isExit(int modeNumber) {
        return modeNumber == 0;
    }
}
