package com.prgms.missionW3D2;

import com.prgms.missionW3D2.voucher.Voucher;
import com.prgms.missionW3D2.voucher.VoucherService;
import com.prgms.missionW3D2.voucher.VoucherType;

import java.util.Arrays;
import java.util.Scanner;

public enum CreateCommandType {
    CREATE_FIXED_VOUCHER("1"){
        void doCommand(VoucherService voucherService) {
            // Fixed Amount Voucher
            System.out.println("Type the amount of the voucher that you want to create.");
            System.out.println("ONLY the long type is allowed.");
            System.out.printf(">> ");
            String amountStr = sc.nextLine();
            try {
                Long amount = Long.parseLong(amountStr);
                Voucher newVoucher = voucherService.createVoucher(VoucherType.FixedAmountVoucher, amount);
                System.out.println("Created "+ newVoucher.getVoucherInfo());
            } catch (NumberFormatException e) {
                System.out.println("What you typed is not the long type amount.");
            }
        }
    },
    CREATE_PERCENT_VOUCHER("2"){
        void doCommand(VoucherService voucherService) {
            // Percent Discount Voucher
            System.out.println("Type the percentage of the voucher that you want to create.");
            System.out.println("ONLY the long type is allowed.");
            System.out.printf(">> ");
            String percentStr = sc.nextLine();
            try {
                Long percent = Long.parseLong(percentStr);
                Voucher newVoucher = voucherService.createVoucher(VoucherType.PercentDiscountVoucher, percent);
                System.out.println("Created "+ newVoucher.getVoucherInfo());
            } catch (NumberFormatException e) {
                System.out.println("What you typed is not the long type.");
            }
        }
    },
    EXIT("exit"){
        void doCommand(VoucherService voucherService){System.exit(0);}
    };

    Scanner sc = new Scanner(System.in);
    private String command;

    CreateCommandType(String command){
        this.command = command;
    }

    public static CreateCommandType of(String command) {
        return Arrays.stream(CreateCommandType.values())
                .filter(createCommandType -> createCommandType.getCommand().equals(command))
                .findFirst()
                .orElse(CreateCommandType.EXIT);
    }

    private String getCommand() {
        return command;
    }

    abstract void doCommand(VoucherService voucherService);
}
