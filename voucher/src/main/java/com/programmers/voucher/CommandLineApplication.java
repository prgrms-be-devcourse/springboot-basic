package com.programmers.voucher;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.domain.Type;
import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.VoucherFactory;
import com.programmers.voucher.stream.VoucherStream;

public class CommandLineApplication implements Runnable{
    private final Console console;
    private final VoucherStream voucherStream;
    private final VoucherFactory voucherFactory;

    public CommandLineApplication(Console console, VoucherStream voucherStream, VoucherFactory voucherFactory) {
        this.console = console;
        this.voucherStream = voucherStream;
        this.voucherFactory = voucherFactory;
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
            showListOfVouchers(type);
            createVouchers(type);
            if (type == Type.EXIT) {
                System.out.println("프로그램이 종료됩니다.");
                break;
            }
        }
    }

    private void showListOfVouchers(Type type) {
        if (type == Type.LIST) {
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
    }

    private void createVouchers(Type type) {
        if (type == Type.CREATE) {
            try {
                voucherFactory.createVoucher(console.getVoucherVersion());
            } catch (IllegalArgumentException e) {
                System.out.println();
                System.out.println("=== Error Occurred ===");
                System.out.println(e.getMessage());
            }
        }
    }
}
