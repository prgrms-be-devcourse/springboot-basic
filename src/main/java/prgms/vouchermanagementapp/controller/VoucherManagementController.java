package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;

public class VoucherManagementController {

    private final IOManager ioManager;
    private final RunningState runningState;

    public VoucherManagementController(IOManager ioManager) {
        this.ioManager = ioManager;
        this.runningState = new RunningState();
    }

    public void run() {
        while (runningState.isRunning()) {
            String command = ioManager.getCommand();
            runUserRequest(command);
        }
    }

    public void runUserRequest(String command) {
        CommandType commandType = CommandType.of(command);

        if (commandType.is(CommandType.EXIT)) {
            runExit();
            return;
        }

        if (commandType.is(CommandType.CREATE)) {
            String voucherType = ioManager.getVoucherType();
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }
}
