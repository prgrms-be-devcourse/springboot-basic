package com.programmers.springbootbasic.presentation.view;

import com.programmers.springbootbasic.common.util.NumberParser;
import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.presentation.Command;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class ConsoleApplicationView {
    private static final String BLANK = "";
    private static final String PERCENT = "정률 할인";
    private static final String FIX = "정액 할인";
    private static final String OPENING_MESSAGE = """
            === 바우처 프로그램 ===
            프로그램을 종료하려면 "나가기" 를 입력하세요.
            새로운 바우처을 생성하려면 "생성" 를 입력하세요.
            모든 바우처을 나열하려면 "조회" 를 입력하세요.
                        
            입력:\s""";
    private static final String CREATE_MESSAGE = "바우처을 생성합니다.";
    private static final String LIST_MESSAGE = "바우처들을 조회합니다.";
    private static final String EXIT_MESSAGE = "어플리케이션을 종료합니다.";
    private static final String INPUT_TYPE = """
            바우처의 타입을 입력해주세요.
            정률 할인 쿠폰을 생성하려면 "%s" 을 입력하세요.
            정액 할인 쿠폰을 생성하려면 "%s" 을 입력하세요.
                        
            입력:\s""";

    private static final String INPUT_AMOUNT_OR_PERCENT = """
            할인 금액을 아래 한도에 맞춰 입력하세요.
            정률 할인 시: 1 ~ 100% (퍼센트 제외)
            정액 할인 시: 10 ~ 10,000,000원 (원 제외)
                        
            입력:\s""";
    private static final String CREATED_VOUCHER_INFO = "=== 바우처 생성완료 ===";
    private static final String INVALID_VOUCHER_TYPE = "잘못된 바우처 유형입니다. 현재 입력 값: ";

    private final Console console;

    public ConsoleApplicationView(Console consoleView) {
        this.console = consoleView;
    }

    public Command selectCommand() throws IOException {
        console.print(OPENING_MESSAGE);
        String inputCommand = console.inputLine();
        printNewLine();
        return Command.from(inputCommand);
    }

    public void printNewLine() throws IOException {
        console.printLine(BLANK);
    }

    public void startCreation() throws IOException {
        console.printLine(CREATE_MESSAGE);
        printNewLine();
    }

    public String inputType() throws IOException {
        // 타입 입력
        console.print(String.format(INPUT_TYPE, PERCENT, FIX));
        String voucherType = console.inputLine();
        printNewLine();
        Validator.checkNullOrBlank(voucherType);
        checkInvalidType(voucherType);
        return voucherType;
    }

    private void checkInvalidType(String input) {
        if (input.equals(PERCENT) || input.equals(FIX)) {
            return;
        }
        throw new IllegalArgumentException(INVALID_VOUCHER_TYPE + input);
    }

    public int inputAmountOrPercent() throws IOException {
        // 할인액 or 할인율 입력
        console.print(INPUT_AMOUNT_OR_PERCENT);
        String amountOrPercentInput = console.inputLine();
        printNewLine();
        return NumberParser.parseToInt(amountOrPercentInput);
    }

    public void printCreatedVoucherId(UUID voucherId) throws IOException {
        console.printLine(CREATED_VOUCHER_INFO);
        printVoucherId(voucherId);
        printNewLine();
    }

    private void printVoucherId(UUID voucherId) throws IOException {
        console.printLine(String.valueOf(voucherId));
    }

    public void listVouchers(VoucherResponses responses) throws IOException {
        console.printLine(LIST_MESSAGE);
        for (VoucherResponse response : responses.voucherResponses()) {
            printVoucherResponse(response);
        }
        printNewLine();
    }

    private void printVoucherResponse(VoucherResponse response) throws IOException {
        console.printLine(response.toString());
    }

    public void exit() throws IOException {
        console.printLine(EXIT_MESSAGE);
    }

    public void printErrorMessage(String message) throws IOException {
        console.printLine(message);
        printNewLine();
    }
}
