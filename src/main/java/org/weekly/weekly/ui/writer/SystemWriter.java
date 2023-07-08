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
        printMenu(ManageMenu.values(), PrintMessageType.MANAGE_PROGRAM);
    }

    public void printVoucherProgram() {
        printMenu(VoucherMenu.values(), PrintMessageType.VOUCHER_PROGRAM);
    }

    public void printCustomerProgram() {
        printMenu(CustomerMenu.values(), PrintMessageType.CUSTOMER_PROGRAM);
    }

    public void printErrorMessage(String errorMsg) {
        logger.warn(errorMsg);
        println(PrintMessageType.EMPTY.getMessage());
        println(errorMsg);
    }

    public void printCreateVoucher(DiscountType discountType) {
        println(PrintMessageType.EMPTY.getMessage());
        println(PrintMessageType.CREATE_VOUCHER.getMessage() + discountType.getInputExampleMessage());
        print(PrintMessageType.INPUT_MESSAGE.getMessage());
    }

    public void printSelectDiscount() {
        println(PrintMessageType.EMPTY.getMessage());
        println(PrintMessageType.DISCOUNT_SELECT.getMessage());
        Arrays.stream(DiscountType.values())
                .forEach(discountMap -> System.out.println(discountMap.getSelectMessage()));
    }

    public void printEmailInputMessage() {
        println(PrintMessageType.EMAIL_INPUT.getMessage());
    }

    public void printNewEmailInputMessage() {
        println(PrintMessageType.NEW_EMAIL_INPUT.getMessage());
    }

    public void printNameInputMessage() {
        println(PrintMessageType.NAME_INPUT.getMessage());
    }

    public void printReuslt(String result) {
        println(PrintMessageType.EMPTY.getMessage());
        println(result);
    }

    public void printDeleteMessage() {
        println(PrintMessageType.DELETE.getMessage());
    }


    private void printMenu(Menu[] menus, PrintMessageType programMessage) {
        println(PrintMessageType.EMPTY.getMessage());
        println(programMessage.getMessage());
        Arrays.stream(menus)
                .forEach(menu -> System.out.println(menu.printMessage()));
    }
}
