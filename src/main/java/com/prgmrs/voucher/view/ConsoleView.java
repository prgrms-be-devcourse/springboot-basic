package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Component
public class ConsoleView {

    private Scanner sc;
    private VoucherController voucherController;
    private BlacklistController blackListController;

    @Value("${system.blacklist.allow}")
    private boolean blacklistAllow;

    @Value("${system.blacklist.display-uuid}")
    private boolean blacklistShowId;

    @Value("${system.voucher.maximum-fixed-amount}")
    private long maximumFixedAmount;

    @Autowired
    public ConsoleView(VoucherController voucherController, BlacklistController blackListController) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        sc = new Scanner(System.in);
    }

    public String read() {
        return sc.nextLine();
    }

    public void write(String message) {
        System.out.println(message);
    }

    public void run() {
        boolean continueRunning = true;
        while (continueRunning) {
            showCommand();
            ConsoleViewEnum consoleViewEnum = ConsoleViewEnum.findByCommand(read());
            switch (consoleViewEnum) {
                case EXIT_THE_LOOP:
                    continueRunning = false;
                    break;
                case CREATE_THE_VOUCHER:
                    selectVoucher();
                    break;
                case SHOW_THE_LIST:
                    showList();
                    break;
                case SHOW_BLACKLIST:
                    if(blacklistAllow) {
                        showBlacklist();
                    }
                    break;
                default:
                    write("Incorrect command typed.");
                    break;
            }
        }
    }


    public void showCommand() {
        write("=== Voucher Program ===");
        write("Type exit to exit the program.");
        write("Type create to create a new voucher.");
        write("Type list to list all vouchers.");

        if(blacklistAllow) {
            write("Type blacklist to list blacklist.");
        }
    }

    public void showVoucher() {
        write("=== Creating Voucher ===");
        write("Type fixed to create a voucher with fixed amount.");
        write("Type percent to create a voucher with percent discount.");
    }

    private void selectVoucher() {
        boolean continueRunning = true;
        long value;
        Voucher voucher;
        String token;
        UUID uuid;
        while (continueRunning) {
            showVoucher();
            ConsoleViewEnum consoleViewEnum = ConsoleViewEnum.findByCommand(read());
            switch (consoleViewEnum) {
                case CREATE_FIXED_AMOUNT_VOUCHER:
                    write("=== Creating Voucher with fixed amount ===");
                    write(MessageFormat.format("Type amount to create a voucher with fixed amount. maximum value is {0}", maximumFixedAmount));
                    token = read();
                    if (isValidIntegerString(token)) {
                        value = Long.parseLong(token);
                        if(value > maximumFixedAmount) {
                            write("fixed amount exceeded the maximum value.");
                            break;
                        }
                    } else {
                        write("typed amount invalid.");
                        break;
                    }
                    uuid = voucherController.createFixedAmountVoucher(value);
                    voucher = voucherController.findVoucherById(uuid);
                    write("=== Successfully created a new voucher ===");
                    write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
                    write(MessageFormat.format("discount amount : {0}", ((FixedAmountVoucher) voucher).getAmount()));
                    continueRunning = false;
                    break;
                case CREATE_PERCENT_DISCOUNT_VOUCHER:
                    write("=== Creating Voucher with percent discount ===");
                    write("Type percent to create a voucher with percent discount. (1 to 10 without percent sign)");
                    token = read();
                    if (isValidIntegerString(token)) {
                        value = Long.parseLong(token);
                    } else {
                        write("typed amount invalid.");
                        break;
                    }
                    uuid = voucherController.createPercentDiscountVoucher(value);
                    voucher = voucherController.findVoucherById(uuid);
                    write("=== Successfully created a new voucher ===");
                    write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
                    write(MessageFormat.format("discount percent : {0}%", ((PercentDiscountVoucher) voucher).getPercent()));
                    continueRunning = false;
                    break;
                default:
                    write("Incorrect command typed.");
                    break;
            }
        }
    }

    private boolean isValidIntegerString(String token) {
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(token).matches();
    }

    private void showList() {
        Map<UUID, Voucher> voucherHistory = voucherController.findAll();
        if (voucherHistory.isEmpty()) {
            write("list is empty.");
        } else {
            write("============== List of created vouchers ==============");
            write("type    uuid                                 discount");
            voucherHistory.entrySet().stream().forEach(entry -> {
                UUID uuid = entry.getKey();
                Voucher voucher = entry.getValue();
                if (voucher instanceof FixedAmountVoucher fixedAmountVoucher) {
                    write(String.format("fixed   %s %s", uuid, fixedAmountVoucher.getAmount()));

                } else if (voucher instanceof PercentDiscountVoucher percentDiscountVoucher) {
                    write(String.format("percent %s %s%%", uuid, percentDiscountVoucher.getPercent()));
                }
            });
        }
    }

    private void showBlacklist() {
        Map<UUID, String> blacklist = blackListController.findAll();
        if (blacklist.isEmpty()) {
            write("list is empty.");
        } else {
            if(blacklistShowId) {
                write("=========== blacklisted users ===========");
                write("uuid                                 name");
                blacklist.entrySet().stream().forEach(entry -> {
                    UUID uuid = entry.getKey();
                    String name = entry.getValue();
                    write(String.format("%s %s", uuid, name));
                });
            } else {
                AtomicInteger order= new AtomicInteger(1);
                write("=========== blacklisted users ===========");
                write("order name");
                blacklist.entrySet().stream().forEach(entry -> {
                    String name = entry.getValue();
                    write(String.format("%d     %s", order.getAndIncrement(), name));
                });
            }
        }
    }

}
