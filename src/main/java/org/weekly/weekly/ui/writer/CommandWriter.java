package org.weekly.weekly.ui.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.weekly.weekly.util.DiscountType;
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
        println(PrintMsg.EMPTY.getMsg());
        println(PrintMsg.PROGRAM.getMsg());
        Arrays.stream(VoucherMenu.values())
                .forEach(voucherMenu -> System.out.println(voucherMenu.getPrintMsg()));
    }

    public void printErrorMsg(String errorMsg) {
        logger.warn(errorMsg);
        println(PrintMsg.EMPTY.getMsg());
        println(errorMsg);
    }

    public void printCreateVoucher() {
        println(PrintMsg.EMPTY.getMsg());
        print(PrintMsg.CREATE_VOUCHER.getMsg());
    }

    public void printSelectDiscount() {
        println(PrintMsg.EMPTY.getMsg());
        println(PrintMsg.DISCOUNT_SELECT.getMsg());
        Arrays.stream(DiscountType.values())
                .forEach(discountMap -> System.out.println(discountMap.getMsg()));
    }

    public void printReuslt(String result) {
        println(PrintMsg.EMPTY.getMsg());
        println(result);
    }
}
