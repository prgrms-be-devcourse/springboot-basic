package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.domain.Type;
import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.BlackListStream;
import com.programmers.voucher.stream.VoucherStream;

public class CommandLineApplication implements Runnable {
    private final Console console;
    private final VoucherStream voucherStream;
    private final VoucherFactory voucherFactory;
    private final BlackListStream blackListStream;

    public CommandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory, BlackListStream blackListStream) {
        this.console = console;
        this.voucherStream = voucherStream;
        this.voucherFactory = voucherFactory;
        this.blackListStream = blackListStream;
    }

    @Override
    public void run() {
        while (true) {
            Type type;
            try {
                type = console.getCondition();
            } catch (Exception e) {
                System.out.println("[Error Occurred] " + e.getMessage());
                continue;
            }
            doLogic(type);
            if (type == Type.EXIT) {
                System.out.println("프로그램이 종료됩니다.");
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

    private void logicForTypeBlack() {
        System.out.println();
        System.out.println(" === BlackList Customer === ");
        blackListStream.findAll().forEach(c -> System.out.println("- " + c));
    }

    private void logicForTypeList() {
        voucherStream.findAll().forEach(
                (k, v) -> {
                    if (v instanceof FixedAmountVoucher) {
                        System.out.println("[FixedAmountVoucher | Voucher ID] : " + k + " | discount amount : " + ((FixedAmountVoucher) v).getAmount());
                    } else {
                        System.out.println("[PercentDiscountVoucher | ID] : " + k + " | discount percent : " + ((PercentDiscountVoucher) v).getPercent());
                    }
                }
        );
    }

    private void logicForTypeCreate() {
        try {
            voucherFactory.createVoucher(console.getVoucherVersion());
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("=== Error Occurred ===");
            System.out.println(e.getMessage());
        }
    }

}
