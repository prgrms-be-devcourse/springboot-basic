package org.weekly.weekly.ui.writer;

import org.springframework.stereotype.Component;
import org.weekly.weekly.util.PrintMsg;
import org.weekly.weekly.util.VoucherMenu;

import java.util.Arrays;

@Component
public class CommandWriter {
    private void println(String msg) {
        System.out.println(msg);
    }
    private void print(String msg) {System.out.print(msg);}

    public void printVoucherProgram() {
        println(PrintMsg.PROGRAM.getMsg());
        Arrays.stream(VoucherMenu.values())
                .forEach(voucherMenu -> System.out.println(voucherMenu.getPrintMsg()));
        println(PrintMsg.EMPTY.getMsg());
    }

    public void printErrorMsg(String errorMsg) {
        println(errorMsg);
        println(PrintMsg.EMPTY.getMsg());
    }

    public void printCreateVoucher() {
        print(PrintMsg.CREATE_VOUCHER.getMsg());
    }

    public void printSelectDiscount() {
        print(PrintMsg.DISCOUNT_SELECT.getMsg());
    }
}
