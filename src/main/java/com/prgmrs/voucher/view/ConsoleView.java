package com.prgmrs.voucher.view;

import com.prgmrs.voucher.domain.FixedAmountVoucher;
import com.prgmrs.voucher.domain.PercentDiscountVoucher;
import com.prgmrs.voucher.domain.Voucher;
import com.prgmrs.voucher.repository.FixedAmountVoucherRepository;
import com.prgmrs.voucher.repository.PercentDiscountVoucherRepository;
import com.prgmrs.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ConsoleView {

    private Scanner sc;
    private VoucherService voucherService;
    private FixedAmountVoucherRepository fixedAmountVoucherRepository;
    private PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    public ConsoleView(VoucherService voucherService, FixedAmountVoucherRepository fixedAmountVoucherRepository, PercentDiscountVoucherRepository percentDiscountVoucherRepository) {
        this.voucherService = voucherService;
        this.fixedAmountVoucherRepository = fixedAmountVoucherRepository;
        this.percentDiscountVoucherRepository = percentDiscountVoucherRepository;
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
        while(continueRunning) {
            showVoucher();
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
                    voucher = voucherService.createFixedAmountVoucher(value);
                    write("=== Successfully created a new voucher ===");
                    write("voucher id : " + voucher.getVoucherId());
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
                    voucher = voucherService.createPercentDiscountVoucher(value);
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
        Map<UUID, Voucher> fixedAmountVoucherHistory = fixedAmountVoucherRepository.findAll();
        Map<UUID, Voucher> percentDiscountVoucherHistory = percentDiscountVoucherRepository.findAll();
        fixedAmountVoucherHistory.entrySet().stream().forEach(entry -> {
            UUID uuid = entry.getKey();
            Voucher voucher = entry.getValue();
            String stringFormat = String.format("fixed   %s %s", uuid, ((FixedAmountVoucher)voucher).getAmount());
            System.out.println(stringFormat);
        });
        percentDiscountVoucherHistory.entrySet().stream().forEach(entry -> {
            UUID uuid = entry.getKey();
            Voucher voucher = entry.getValue();
            String stringFormat = String.format("percent %s %s%%", uuid, ((PercentDiscountVoucher)voucher).getPercent());
            System.out.println(stringFormat);
        });
    }

}
