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
        console.write("Exit Program. Good Bye.");
    }

    public String getCreateTypeInput() {
        return console.read()
                .strip();
    }

    public double getAmountInput() {
        try {
            return Double.parseDouble(console.read()
                    .strip());
        } catch (NumberFormatException e) {
            throw new AmountException("할인 정도는 정수나 실수로만 입력 가능합니다.");
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
                MessageFormat.format(
                        "- voucherID : {0}, voucherType : {1}, Discount Amount : {2}",
                        voucher.getVoucherId(), voucher.getClass().getSimpleName(), voucher.getAmount())
        );
    }

    public static String getSelectWrongMessage(){
        return SELECT_WRONG;
    }
}
