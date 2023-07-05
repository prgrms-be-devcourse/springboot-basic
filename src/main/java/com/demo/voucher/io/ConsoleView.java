package com.demo.voucher.io;

import com.demo.voucher.domain.Voucher;
import com.demo.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleView implements Input, Output {

    private static final Scanner scanner = new Scanner(System.in);

    private static final String VOUCHER_PROGRAM_START_OUTPUT = "===== 바우처 프로그램 =====";
    private static final String REQUEST_MENU_TYPE_PROMPT = "\n명령 메뉴를 입력해주세요 : ";
    private static final String REQUEST_VOUCHER_TYPE_PROMPT = "생성할 바우처 타입을 입력해주세요 : ";
    private static final String NO_VOUCHER_HISTORY = "등록된 바우처가 없습니다.";
    private static final String EXIT_MESSAGE = "바우처 프로그램을 종료합니다";
    private static final String SPACE = " ";

    @Override
    public String requestMenu() {
        System.out.print(REQUEST_MENU_TYPE_PROMPT);
        return scanner.nextLine();
    }

    @Override
    public String requestVoucherType() {
        System.out.print(REQUEST_VOUCHER_TYPE_PROMPT);
        return scanner.nextLine();
    }

    @Override
    public String getAmount(VoucherType voucherType) {
        System.out.print(voucherType.getRequestAmountDescription());
        return scanner.nextLine();
    }

    @Override
    public void showMenu() {
        System.out.println(VOUCHER_PROGRAM_START_OUTPUT);
        Arrays.stream(CommandType.values())
                .forEach(c -> System.out.println(c.getCommandTypeDescription()));
    }

    @Override
    public void showVoucherType() {
        Arrays.stream(VoucherType.values()).forEach(v -> System.out.println(v.getVoucherCommandAndDescription()));

    }

    @Override
    public void inputError(String errorResponse) {
        System.out.println(errorResponse);
    }

    @Override
    public void showAllVouchers(List<Voucher> voucherHistory) {
        if (voucherHistory.isEmpty()) {
            System.out.println(NO_VOUCHER_HISTORY);
            return;
        }
        voucherHistory.forEach(
                v -> System.out.println(MessageFormat.format("{0}{1}{2}{3}{4}",
                        v.getVoucherId(), SPACE, v.getVoucherType(), SPACE, v.getDiscountInfo())));
    }

    @Override
    public void showExitMessage() {
        System.out.println(EXIT_MESSAGE);
    }
}
