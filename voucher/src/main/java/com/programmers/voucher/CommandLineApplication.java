package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.CommandType;
import com.programmers.voucher.domain.voucher.VoucherEnum;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.blacklist.BlackListStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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
            case BLACKLIST -> {
                showBlackList();
            }
        }
    }

    private void create() {
        try {
            Integer inputVersion = console.getVoucherVersion();
            VoucherEnum voucherEnum = decideVoucherType(inputVersion);
            createFixedAmountVoucher(voucherEnum);
            createPercentDiscountVoucher(voucherEnum);
        } catch (IllegalArgumentException e) {
            printer.printError(e);
            System.out.println(e.getMessage());
        }
    }

    private void createFixedAmountVoucher(VoucherEnum voucherEnum) {
        Integer inputNumber;
        if (voucherEnum == VoucherEnum.FIXED) {
            inputNumber = console.getAmount();
            voucherFactory.createVoucher(voucherEnum, inputNumber);
        }
    }

    private void createPercentDiscountVoucher(VoucherEnum voucherEnum) {
        Integer inputNumber;
        if (voucherEnum == VoucherEnum.PERCENT) {
            inputNumber = console.getRate();
            voucherFactory.createVoucher(voucherEnum, inputNumber);
        }
    }

    private VoucherEnum decideVoucherType(Integer inputVersion) {
        return VoucherEnum.decideVoucherType(inputVersion).orElseThrow(
                () -> {
                    log.info("지원하지 않는 버전, [user input] : {}", inputVersion);
                    return new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.");
                }
        );
    }

    private void showList() {
        printer.printListOfVoucher(voucherStream.findAll());
    }

    private void showBlackList() throws IOException {
        printer.printBlackList(blackListStream.findAll());
    }

}
