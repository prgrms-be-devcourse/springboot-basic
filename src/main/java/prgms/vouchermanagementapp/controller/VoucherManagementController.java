package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.voucher.VoucherCreator;

public class VoucherManagementController {

    private final IOManager ioManager;
    private final RunningState runningState;
    private final VoucherCreator voucherCreator;

    public VoucherManagementController(IOManager ioManager, VoucherCreator voucherCreator) {
        this.ioManager = ioManager;
        this.voucherCreator = voucherCreator;
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
            String voucherTypeIndex = ioManager.askVoucherTypeIndex();
            voucherCreator.create(voucherTypeIndex);
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }
}
