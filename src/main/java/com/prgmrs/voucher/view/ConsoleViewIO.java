package com.prgmrs.voucher.view;

import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleViewIO {
    Scanner sc = new Scanner(System.in);
    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        return sc.nextLine();
    }

    public void showCommand(boolean isBlacklistAllow) {
        write("=== Voucher Program ===");
        write("Type exit to exit the program.");
        write("Type create to create a new voucher.");
        write("Type list to list all vouchers.");
        if(isBlacklistAllow) {
            write("Type blacklist to list blacklist.");
        }
    }

    public void showVoucherCreationMessage() {
            write("Type fixed to create a voucher with fixed amount.");
            write("Type percent to create a voucher with percent discount.");
    }

    public void showSpecificCreationMessage(ConsoleViewVoucherCreationEnum consoleViewVoucherCreationEnum, long maximumFixedAmount) {
        if(ConsoleViewVoucherCreationEnum.CREATE_FIXED_AMOUNT_VOUCHER == consoleViewVoucherCreationEnum) {
            write("=== Creating Voucher with fixed amount ===");
            write(MessageFormat.format("Type amount to create a voucher with fixed amount. maximum value is {0}", maximumFixedAmount));
            return;
        }

        if (ConsoleViewVoucherCreationEnum.CREATE_PERCENT_DISCOUNT_VOUCHER == consoleViewVoucherCreationEnum) {
            write("=== Creating Voucher with percent discount ===");
            write("Type percent to create a voucher with percent discount. (1 to 100 without percent sign)");
        }
    }

    public void showVoucherResult(Voucher voucher) {
        write("=== Successfully created a new voucher ===");
        if (voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
            write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
            write(MessageFormat.format("discount amount : {0}", fixedAmountVoucher.getAmount()));
            return;
        }
        if (voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
            write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
            write(MessageFormat.format("discount percent : {0}%", percentDiscountVoucher.getPercent()));
        }
    }

    public void showList(Map<UUID, Voucher> voucherHistory) {
        if (voucherHistory.isEmpty()) {
            write("list is empty.");
            return;
        }
        write("============== List of created vouchers ==============");
        write("type    uuid                                 discount");
        voucherHistory.entrySet().stream().forEach(entry -> {
            UUID uuid = entry.getKey();
            Voucher voucher = entry.getValue();
            if (voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
                write(String.format("fixed   %s %s", uuid, fixedAmountVoucher.getAmount()));
                return;
            }

            if (voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
                write(String.format("percent %s %s%%", uuid, percentDiscountVoucher.getPercent()));
            }
        });

    }

    public void showBlacklist(Map<UUID, String> blacklist, boolean blacklistShowId) {
        if (blacklist.isEmpty()) {
            write("list is empty.");
            return;
        }

        if(blacklistShowId) {
            write("=========== blacklisted users ===========");
            write("uuid                                 name");
            blacklist.entrySet().stream().forEach(entry -> {
                UUID uuid = entry.getKey();
                String name = entry.getValue();
                write(String.format("%s %s", uuid, name));
            });
            return;
        }

        AtomicInteger order= new AtomicInteger(1);
        write("=========== blacklisted users ===========");
        write("order name");
        blacklist.entrySet().stream().forEach(entry -> {
            String name = entry.getValue();
            write(String.format("%d     %s", order.getAndIncrement(), name));
        });
    }
}
