package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public enum ProgramFunction {
    VOUCHER("voucher", "바우처 모드", OutputConsole::startVoucherMode),
    CUSTOMER("customer", "고객 모드", OutputConsole::startCustomerMode),
    WALLET("wallet", "지갑 모드", OutputConsole::startWalletMode),
    EXIT("exit", "프로그램을 종료합니다.", OutputConsole::printProgramEnd);
    private final String fun;
    private final String description;
    private final Consumer<OutputConsole> outputConsoleConsumer;
    private final static Logger logger = LoggerFactory.getLogger(ProgramFunction.class);

    ProgramFunction(String fun, String description, Consumer<OutputConsole> outputConsoleConsumer) {
        this.fun = fun;
        this.description = description;
        this.outputConsoleConsumer = outputConsoleConsumer;
    }

    public static ProgramFunction findByCode(String fun) {
        String lowerFun = fun.toLowerCase();
        return Arrays.stream(values())
                .filter(option -> option.fun.equals(lowerFun))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("해당 명령어가 존재하지 않습니다.");
                    return new NoSuchElementException("해당 명령어가 존재하지 않습니다.");
                });
    }

    public void execute(OutputConsole outputConsole) {
        this.outputConsoleConsumer.accept(outputConsole);
    }
}
