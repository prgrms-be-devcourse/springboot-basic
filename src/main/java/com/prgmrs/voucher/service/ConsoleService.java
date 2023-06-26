package com.prgmrs.voucher.service;

import com.prgmrs.voucher.domain.FixedAmountVoucher;
import com.prgmrs.voucher.domain.PercentDiscountVoucher;
import com.prgmrs.voucher.domain.Voucher;
import com.prgmrs.voucher.repository.FixedAmountVoucherRepository;
import com.prgmrs.voucher.repository.PercentDiscountVoucherRepository;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Service
public class ConsoleService {

    private Scanner sc;
    private VoucherService voucherService;
    private FixedAmountVoucherRepository fixedAmountVoucherRepository;
    private PercentDiscountVoucherRepository percentDiscountVoucherRepository;

    public ConsoleService(Scanner sc, VoucherService voucherService, FixedAmountVoucherRepository fixedAmountVoucherRepository, PercentDiscountVoucherRepository percentDiscountVoucherRepository) {
        this.sc = sc;
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
            ConsoleServiceEnum consoleServiceEnum = ConsoleServiceEnum.findByCommand(read());
            switch(consoleServiceEnum) {
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
        while(continueRunning) {
            showVoucher();
            ConsoleServiceEnum consoleServiceEnum = ConsoleServiceEnum.findByCommand(read());
            switch(consoleServiceEnum) {
                case CREATE_FIXED_AMOUNT_VOUCHER:
                    write("=== Creating Voucher with fixed amount ===");
                    write("Type amount to create a voucher with fixed amount.");
                    value = Long.parseLong(read());
                    voucher = voucherService.createFixedAmountVoucher(value);
                    write("=== Successfully created a new voucher ===");
                    write("voucher id : " + voucher.getVoucherId());
                    write(MessageFormat.format("discount amount : {0}", ((FixedAmountVoucher) voucher).getAmount()));
                    continueRunning = false;
                    break;
                case CREATE_PERCENT_DISCOUNT_VOUCHER:
                    write("=== Creating Voucher with percent discount ===");
                    write("Type percent to create a voucher with percent discount. (without percent sign)");
                    value = Long.parseLong(read());
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
