package org.weekly.weekly.ui.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.util.PrintMessage;
import org.weekly.weekly.util.VoucherMenu;

import java.util.Arrays;

@Component
public class CommandWriter {
    private final Logger logger = LoggerFactory.getLogger(CommandWriter.class);
    private void println(String msg) {
        System.out.println(msg);
    }
    private void print(String msg) {System.out.print(msg);}

    public void printVoucherProgram() {
        println(PrintMessage.EMPTY.getMessage());
        println(PrintMessage.PROGRAM.getMessage());
        Arrays.stream(VoucherMenu.values())
                .forEach(voucherMenu -> System.out.println(voucherMenu.getPrintMsg()));
    }

    public void printErrorMsg(String errorMsg) {
        logger.warn(errorMsg);
        println(PrintMessage.EMPTY.getMessage());
        println(errorMsg);
    }

    public void printCreateVoucher(DiscountType discountType) {
        println(PrintMessage.EMPTY.getMessage());
        println(PrintMessage.CREATE_VOUCHER.getMessage() + discountType.getInputExampleMessage());
        print(PrintMessage.INPUT_MESSAGE.getMessage());
    }

    public void printSelectDiscount() {
        println(PrintMessage.EMPTY.getMessage());
        println(PrintMessage.DISCOUNT_SELECT.getMessage());
        Arrays.stream(DiscountType.values())
                .forEach(discountMap -> System.out.println(discountMap.getSelectMessage()));
    }

    public void printReuslt(String result) {
        println(PrintMessage.EMPTY.getMessage());
        println(result);
    }
}
