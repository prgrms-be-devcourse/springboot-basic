package org.prgrms.kdt.io;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class IOManager {

    private static final String CONSOLE_START = """
            === Voucher Program ===
            Type 'exit' to exit the program.
            Type 'create' to create a new voucher.
            Type 'list' to list all vouchers.""";

    private static final String SELECT_WRONG = "올바른 입력이 아닙니다. 보기 중에 입력해주세요.";
    private static final String CREATE_MESSAGE = """
            Type 'fix' to create FixedAmountVoucher.
            Type 'percent' to create PercentAmountVoucher.
            Type 'g' to go back to main.""";

    private static final String AMOUNT_MESSAGE = "할인하고자 하는 만큼의 수를 입력해주세요.";
    private static final String AMOUNT_ERROR = "할인 정도는 정수나 실수로만 입력 가능합니다.";
    private static final String EXIT_APP = "Exit Program. Good Bye.";

    private final Console console;

    public IOManager(Console console) {
        this.console = console;
    }

    public String getInput() {
        return console.read()
                .toLowerCase()
                .strip();
    }

    public void writeStartMessage() {
        console.write(CONSOLE_START);
    }

    public void writeEndMessage() {
        console.write(EXIT_APP);
    }

    public String getCreateTypeInput() {
        console.write(CREATE_MESSAGE);
        return console.read()
                .strip();
    }

    public double getAmountInput() {
        console.write(AMOUNT_MESSAGE);
        try {
            return Double.parseDouble(console.read()
                    .strip());
        } catch (NumberFormatException e) {
            throw new AmountException(AMOUNT_ERROR);
        }
    }

    public void writeExceptionMessage(String message) {
        console.write(message);
    }

    public void writeMessage(String message) {
        console.write(message);
    }

    public void writeVoucherInfo(Voucher voucher) {
        console.write(
                MessageFormat.format("[voucher Type] : {0}, [Amount] : {1}",
                        voucher.getClass().getSimpleName(), voucher.getAmount()));
    }

    public static String getSelectWrongMessage() {
        return SELECT_WRONG;
    }
}
