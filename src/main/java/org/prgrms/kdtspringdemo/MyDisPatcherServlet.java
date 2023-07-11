package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyDisPatcherServlet implements CommandLineRunner {
    private static final String INIT_MESSAGE = """
            === Voucher Program ===
            EXIT -> 프로그램 종료
            VOUCHER -> 바우처 관련 서비스
            CUSTOMER -> 소비자 관련 서비스
            """;
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";

    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherController voucherController;

    public MyDisPatcherServlet(VoucherController voucherController) {
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = voucherConsole.inputMainCommand(INIT_MESSAGE);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputMainCommand(INIT_MESSAGE);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN_MESSAGE);
            case VOUCHER -> voucherController.run();
        }
    }
}
