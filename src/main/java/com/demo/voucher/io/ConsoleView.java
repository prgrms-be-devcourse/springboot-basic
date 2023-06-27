package com.demo.voucher.io;

import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleView implements Input, Output {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String VOUCHER_PROGRAM_START_OUTPUT = "\n=== Voucher Program ===";
    private static final String NO_VOUCHER_HISTORY = "등록된 바우처가 없습니다.";
    private static final String SPACE = " ";

    @Override
    public String getMenu() {
        return scanner.nextLine();
    }

    @Override
    public String getVoucherType(String requestVoucherTypePrompt) {
        System.out.println(requestVoucherTypePrompt);
        return scanner.nextLine();
    }

    @Override
    public String getAmount(VoucherType voucherType) {
        System.out.println(voucherType.getAmountDescription());
        return scanner.nextLine();
    }

    @Override
    public void showMenu() {
        System.out.println(VOUCHER_PROGRAM_START_OUTPUT);
        Arrays.stream(CommandType.values())
                .forEach(c -> System.out.println(c.toString()));
    }

    @Override
    public void showVoucherType() {
        Arrays.stream(VoucherType.values()).forEach(v -> System.out.println(v.toString()));

    }

    @Override
    public void inputError(String errorResponse) {
        System.out.println(errorResponse);
    }

    @Override
    public void showAllVouchers(Map<UUID, Voucher> voucherHistory) {
        if (voucherHistory.isEmpty()) {
            System.out.println(NO_VOUCHER_HISTORY);
            return;
        }
        voucherHistory.values().forEach(v -> System.out.println(MessageFormat.format("{0}{1}{2}{3}{4}", v.getVoucherId(), SPACE, v.getVoucherType(), SPACE, v.getDiscountInfo())));
    }
}
