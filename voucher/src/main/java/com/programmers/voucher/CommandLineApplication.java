package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.Type;
import com.programmers.voucher.domain.voucher.*;
import com.programmers.voucher.stream.BlackListStream;
import com.programmers.voucher.stream.VoucherStream;

public class CommandLineApplication implements Runnable {
    private final Console console;
    private final VoucherStream voucherStream;
    private final VoucherFactory voucherFactory;
    private final BlackListStream blackListStream;
    private final Printer printer;

    public CommandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory, BlackListStream blackListStream, Printer printer) {
        this.console = console;
        this.voucherStream = voucherStream;
        this.voucherFactory = voucherFactory;
        this.blackListStream = blackListStream;
        this.printer = printer;
    }

    @Override
    public void run() {
        while (true) {
            Type type;
            try {
                String inputCondition = console.getCondition();
                type = Type.validateInput(inputCondition);
            } catch (Exception e) {
                printer.printError(e);
                continue;
            }
            doLogic(type);
            if (type == Type.EXIT) {
                printer.printEndMessage();
                break;
            }
        }
    }

    private void doLogic(Type type) {
        switch (type) {
            case CREATE -> {
                logicForTypeCreate();
            }
            case LIST -> {
                logicForTypeList();
            }
            case BLACK -> {
                logicForTypeBlack();
            }
        }
    }

    private void logicForTypeCreate() {
        try {
            Integer inputVersion = console.getVoucherVersion();
            VoucherEnum voucherEnum = VoucherEnum.decideVoucherType(inputVersion);
            voucherFactory.createVoucher(voucherEnum);
        } catch (IllegalArgumentException e) {
            printer.printError(e);
            System.out.println(e.getMessage());
        }
    }

    private void logicForTypeList() {
        printer.printListOfVoucher(voucherStream.findAll());
    }

    private void logicForTypeBlack() {
        printer.printBlackList(blackListStream.findAll());
    }

}
