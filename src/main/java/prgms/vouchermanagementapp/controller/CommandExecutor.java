package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;

public class CommandExecutor {

    private final IOManager ioManager;
    private final VoucherManager voucherManager;
    private final RunningState runningState;

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
            runCreate();
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }

    private void runCreate() {
        // 어떤타입을 고를지 입력받는다.
        // 타입에따라 추가정보를 입력 받고(입력은 똑같이 받아도 가능)
        voucherManager.createVoucher();
    }
}
