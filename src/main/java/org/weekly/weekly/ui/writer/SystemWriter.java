package org.weekly.weekly.ui.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.weekly.weekly.util.*;
import org.weekly.weekly.voucher.domain.DiscountType;

import java.util.Arrays;

@Component
@ConditionalOnProperty(value="command.write", havingValue = "system")
public class SystemWriter {
    private final Logger logger = LoggerFactory.getLogger(SystemWriter.class);
    private void println(String msg) {
        System.out.println(msg);
    }
    private void print(String msg) {System.out.print(msg);}

    public void printMangeProgram() {
        printMenu(ManageMenu.values(), PrintMessage.MANAGE_PROGRAM);
    }

    public void printVoucherProgram() {
        printMenu(VoucherMenu.values(), PrintMessage.VOUCHER_PROGRAM);
    }

    public void printCustomerProgram() {
        printMenu(CustomerMenu.values(), PrintMessage.CUSTOMER_PROGRAM);
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

    private void printMenu(Menu[] menus, PrintMessage programMessage) {
        println(PrintMessage.EMPTY.getMessage());
        println(programMessage.getMessage());
        Arrays.stream(menus)
                .forEach(menu -> System.out.println(menu.printMessage()));
    }
}
