package org.weekly.weekly.ui.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.util.PrintMsg;
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
        println(PrintMsg.EMPTY.getMessage());
        println(PrintMsg.PROGRAM.getMessage());
        Arrays.stream(VoucherMenu.values())
                .forEach(voucherMenu -> System.out.println(voucherMenu.getPrintMsg()));
    }

    public void printErrorMsg(String errorMsg) {
        logger.warn(errorMsg);
        println(PrintMsg.EMPTY.getMessage());
        println(errorMsg);
    }

    public void printCreateVoucher(DiscountType discountType) {
        println(PrintMsg.EMPTY.getMessage());
        println(PrintMsg.CREATE_VOUCHER.getMessage() + discountType.getInputExampleMessage());
        print(PrintMsg.INPUT_MESSAGE.getMessage());
    }

    public void printSelectDiscount() {
        println(PrintMsg.EMPTY.getMessage());
        println(PrintMsg.DISCOUNT_SELECT.getMessage());
        Arrays.stream(DiscountType.values())
                .forEach(discountMap -> System.out.println(discountMap.getSelectMessage()));
    }

    public void printReuslt(String result) {
        println(PrintMsg.EMPTY.getMessage());
        println(result);
    }
}
