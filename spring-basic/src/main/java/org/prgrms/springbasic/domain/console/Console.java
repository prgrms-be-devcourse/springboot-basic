package org.prgrms.springbasic.domain.console;

import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import static org.prgrms.springbasic.utils.enumm.ConsoleMessage.*;

@Component
public class Console implements Input, Output {

    @Override
    public String takeInput() {
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    @Override
    public void printInitMessage() {
        System.out.println(INIT_MESSAGE.getMessage());
    }

    @Override
    public void printVoucherType() {
        System.out.println(VOUCHER_COMMAND_LIST.getMessage());
    }

    @Override
    public void printDiscountInput(VoucherType type) {
        switch(type) {
            case FIXED:
                System.out.println(CREATE_FIXED_VOUCHER.getMessage());
                break;
            case PERCENT:
                System.out.println(CREATE_PERCENT_VOUCHER.getMessage());
                break;
            default:
                break;
        }
    }

    @Override
    public void printNameInput() {
        System.out.println(REGISTER_BLACK.getMessage());
    }

    @Override
    public <T> void printData(List<T> data) {
        data.forEach(System.out::print);
    }

    @Override
    public void printErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
