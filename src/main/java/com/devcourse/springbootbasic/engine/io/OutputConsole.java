package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class OutputConsole {

    public static final String END_GAME = "\n프로그램을 종료합니다.";
    public static final String CREATION_DONE = "\n바우처가 생성되었습니다.";

    private TextTerminal output = TextIoFactory.getTextTerminal();

    public void printLine() {
        output.println();
    }

    public void printMenus() {
        printMessage("\n=== Voucher Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new voucher.");
        printMessage("Type list to list all vouchers.");
    }

    public void printMessage(String message) {
        output.println(message);
    }

    public void printError(InvalidDataException e) {
        printMessage(e.getMessage());
        printLine();
    }

    public void printVoucher(Voucher voucher) {
        printMessage(voucher.toString() + CREATION_DONE);
    }

    public void endPlatform() {
        printMessage(END_GAME);
        printLine();
    }

    public void printVouchers(List<Voucher> voucherList) {
        printMessage("-Your Voucher List-");
        voucherList.forEach(this::printVoucher);
        printLine();
    }

    public void printVoucherTypes() {
        printMessage("\n--- Voucher Type ---");
    }

    public void printVoucherDiscount(VoucherType voucherType) {
        printMessage(MessageFormat.format(
                "Voucher Discount {0} : ", voucherType.getTypeString())
        );
    }
}
