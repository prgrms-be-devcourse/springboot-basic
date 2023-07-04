package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.enums.CommandType;
import com.programmers.voucher.operator.BlackListOperator;
import com.programmers.voucher.operator.CustomerOperator;
import com.programmers.voucher.operator.VoucherOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private final Console console;
    private final VoucherOperator voucherOperator;
    private final CustomerOperator customerOperator;
    private final BlackListOperator blackListOperator;
    private final Printer printer;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CommandLineApplication(Console console, VoucherOperator voucherOperator, CustomerOperator customerOperator, BlackListOperator blackListOperator, Printer printer) {
        this.console = console;
        this.voucherOperator = voucherOperator;
        this.customerOperator = customerOperator;
        this.blackListOperator = blackListOperator;
        this.printer = printer;
    }

    @Override
    public void run(String... args) {
        while (true) {
            CommandType commandType;
            try {
                String inputCondition = console.getCondition();
                commandType = convertAndValidateInput(inputCondition);
                doLogic(commandType);
            } catch (Exception e) {
                log.info("Exception Occurred ", e);
                printer.printError(e);
                continue;
            }
            if (commandType == CommandType.EXIT) {
                printer.printEndMessage();
                break;
            }
        }
    }

    private CommandType convertAndValidateInput(String inputCondition) {
        return CommandType.convertStringToCommandType(inputCondition).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 type 입니다. 다시 확인 부탁드립니다.")
        );
    }

    private void doLogic(CommandType commandType) throws IOException {
        switch (commandType) {
            case VOUCHER -> voucherOperator.voucherOperation();
            case CUSTOMER -> customerOperator.customerOperation();
            case BLACKLIST -> blackListOperator.showBlackList();
        }
    }


}
