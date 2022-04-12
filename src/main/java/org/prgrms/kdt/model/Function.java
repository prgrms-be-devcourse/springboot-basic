package org.prgrms.kdt.model;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.Utility;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public enum Function {
    exit(" to exit the program") {
        @Override
        public Boolean doFunction(VoucherService voucherService) {
            Function.getOutput().printExitMessage();
            return true;
        }
    },
    create(" to create a new voucher") {
        @Override
        public Boolean doFunction(VoucherService voucherService) {
            Function.getOutput().printVoucherType();
            Input input = Function.getInput();
            try {
                createVoucherByVoucherType(input.voucherType(), input.amount(), voucherService);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    },
    list(" to list all vouchers") {
        @Override
        public Boolean doFunction(VoucherService voucherService) {
            Function.printVoucherList(voucherService.getVoucherList());
            return false;
        }
    };

    private final String explain;

    Function(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public static boolean hasFunction(String function) {
        return Arrays.stream(Function.values())
                .anyMatch(f -> f.name().equals(function));
    }

    public abstract Boolean doFunction(VoucherService voucherService);

    private static Input getInput() {
        return new Input();
    }

    private static Output getOutput() {
        return new Output();
    }

    private static void createVoucherByVoucherType(String voucherType, String discountAmount, VoucherService voucherService) {
        if (!VoucherType.hasVoucherType(voucherType)) {
            throw new IllegalArgumentException("Type right VoucherType number");
        }
        voucherService.createVoucher(UUID.randomUUID(),
                Utility.toInt(voucherType),
                Utility.toInt(discountAmount));
    }

    private static void printVoucherList(Map<UUID, Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            getOutput().printVoucherListEmptyError();
            return;
        }
        for (Map.Entry<UUID, Voucher> entry : voucherList.entrySet()) {
            Voucher voucher = entry.getValue();
            System.out.println(voucher.toString());
        }
    }
}
