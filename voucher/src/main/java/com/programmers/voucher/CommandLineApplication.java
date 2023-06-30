package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.CommandType;
import com.programmers.voucher.domain.voucher.*;
import com.programmers.voucher.stream.BlackListStream;
import com.programmers.voucher.stream.VoucherStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;

public class CommandLineApplication implements CommandLineRunner {
    private final Console console;
    private final VoucherStream voucherStream;
    private final VoucherFactory voucherFactory;
    private final BlackListStream blackListStream;
    private final Printer printer;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CommandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory, BlackListStream blackListStream, Printer printer) {
        this.console = console;
        this.voucherStream = voucherStream;
        this.voucherFactory = voucherFactory;
        this.blackListStream = blackListStream;
        this.printer = printer;
    }
    @Override
    public void run(String... args) throws Exception {
        while (true) {
            CommandType commandType;
            try {
                String inputCondition = console.getCondition();
                commandType = convertAndValidateInput(inputCondition);
                doLogic(commandType);
            } catch (Exception e) {
                log.info("Error Occurred : {}", e.getMessage());
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
            case CREATE -> {
                create();
            }
            case LIST -> {
                showList();
            }
            case BLACK -> {
                showBlackList();
            }
        }
    }

    private void create() {
        try {
            Integer inputVersion = console.getVoucherVersion();
            VoucherEnum voucherEnum = VoucherEnum.decideVoucherType(inputVersion);
            voucherFactory.createVoucher(voucherEnum);
        } catch (IllegalArgumentException e) {
            printer.printError(e);
            System.out.println(e.getMessage());
        }
    }

    private void showList() {
        printer.printListOfVoucher(voucherStream.findAll());
    }

    private void showBlackList() throws IOException {
        printer.printBlackList(blackListStream.findAll());
    }

}
