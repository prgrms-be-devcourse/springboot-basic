package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.voucher.VoucherManager;

public class CommandExecutor {

    private final IOManager ioManager;
    private final RunningState runningState;
    private final VoucherManager voucherManager;

    public CommandExecutor(IOManager ioManager, VoucherManager voucherManager) {
        this.ioManager = ioManager;
        this.voucherManager = voucherManager;
        this.runningState = new RunningState();
    }

    public void run() {
        while (runningState.isRunning()) {
            String command = ioManager.askCommand();
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
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }
}
