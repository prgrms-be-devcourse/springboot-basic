package com.prgmrs.voucher.view;

import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.PercentDiscountVoucher;
import com.prgmrs.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class ConsoleView {

    private Scanner sc;
    private VoucherController voucherController;

    @Autowired
    public ConsoleView(VoucherController voucherController) {
        this.voucherController = voucherController;
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
        while(continueRunning) {
            showCommand();
            ConsoleViewEnum consoleViewEnum = ConsoleViewEnum.findByCommand(read());
            switch(consoleViewEnum) {
                case EXIT_THE_LOOP:
                    continueRunning = false;
                    break;
                case CREATE_THE_VOUCHER:
                    showVoucher();
                    selectVoucher();
                    break;
                case SHOW_THE_LIST:
                    showList();
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
        while(continueRunning) {
            ConsoleViewEnum consoleViewEnum = ConsoleViewEnum.findByCommand(read());
            switch(consoleViewEnum) {
                case CREATE_FIXED_AMOUNT_VOUCHER:
                    write("=== Creating Voucher with fixed amount ===");
                    write("Type amount to create a voucher with fixed amount. (between 1 to 100)");
                    token = read();
                    if(isValidIntegerString(token)) {
                        value = Long.parseLong(token);
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
                    write("Type percent to create a voucher with percent discount. (without percent sign)");
                    token = read();
                    if(isValidIntegerString(token)) {
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
        write("=== List of created vouchers ===");
        write("type    uuid                                 discount");
        Map<UUID, Voucher> voucherHistory = voucherController.findAll();
        voucherHistory.entrySet().stream().forEach(entry -> {
            UUID uuid = entry.getKey();
            Voucher voucher = entry.getValue();
            if(voucher instanceof FixedAmountVoucher) {
                System.out.println(String.format("fixed   %s %s", uuid, ((FixedAmountVoucher)voucher).getAmount()));

            } else if (voucher instanceof PercentDiscountVoucher) {
                System.out.println(String.format("percent %s %s%%", uuid, ((PercentDiscountVoucher)voucher).getPercent()));
            }
        });
    }

}
