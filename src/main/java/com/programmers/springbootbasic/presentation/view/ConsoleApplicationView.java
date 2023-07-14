package com.programmers.springbootbasic.presentation.view;

import com.programmers.springbootbasic.common.util.LocalDateTimeParser;
import com.programmers.springbootbasic.common.util.NumberParser;
import com.programmers.springbootbasic.common.util.Validator;
import com.programmers.springbootbasic.presentation.Command;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

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
    private static final String INPUT_NAME = """
            바우처의 이름을 입력하세요. (1자 이상)
                        
            입력:\s""";

    private static final String INPUT_MINIMUM_PRICE_CONDITION = """
            바우처의 사용 최소금액 조건을 입력하세요.
            미입력 시 0원으로 설정됩니다.
                        
            입력:\s""";

    private static final String INPUT_EXPIRED_DATETIME = """
            바우처의 만료기한을 입력하세요. (예시: yyyy-MM-dd, yyyy-MM-dd HH:mm:ss)
            yyyy-MM-dd 입력 시, yyyy-MM-dd 23:59:59.999로 설정됩니다.
                        
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

    public void startCreation() throws IOException {
        console.printLine(CREATE_MESSAGE);
    }

    public String inputType() throws IOException {
        // 타입 입력
        console.print(String.format(INPUT_TYPE, PERCENT, FIX));
        String voucherType = console.inputLine();
        Validator.checkNullOrBlank(voucherType);
        checkInvalidType(voucherType);
        return voucherType;
    }

    public String inputName() throws IOException {
        // 이름 입력
        console.print(INPUT_NAME);
        String name = console.inputLine();
        Validator.checkNullOrBlank(name);
        return name;
    }

    public long inputMinimumPriceCondition() throws IOException {
        // 최소 금액 입력
        console.print(INPUT_MINIMUM_PRICE_CONDITION);
        String minimumPriceConditionInput = console.inputLine();
        return NumberParser.parseToMinimumPriceCondition(minimumPriceConditionInput);
    }

    public LocalDateTime inputExpiredAt() throws IOException {
        // 만료기한 입력
        console.print(INPUT_EXPIRED_DATETIME);
        String expiredAtInput = console.inputLine();
        return LocalDateTimeParser.parseToLocalDateTime(expiredAtInput);
    }

    public int inputAmountOrPercent() throws IOException {
        // 할인액 or 할인율 입력
        console.print(INPUT_AMOUNT_OR_PERCENT);
        String amountOrPercentInput = console.inputLine();
        return NumberParser.parseToAmountOrPercent(amountOrPercentInput);
    }

    public void printCreatedVoucher(VoucherResponse response) throws IOException {
        console.printLine(CREATED_VOUCHER_INFO);
        printVoucherResponse(response);
        printNewLine();
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

    public void printNewLine() throws IOException {
        console.printLine(BLANK);
    }

    private void checkInvalidType(String input) {
        if (input.equals(PERCENT) || input.equals(FIX)) {
            return;
        }
        throw new IllegalArgumentException(INVALID_VOUCHER_TYPE + input);
    }
}
