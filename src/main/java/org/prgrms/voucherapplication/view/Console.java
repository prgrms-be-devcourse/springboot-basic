package org.prgrms.voucherapplication.view;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.exception.InvalidMenuException;
import org.prgrms.voucherapplication.exception.InvalidVoucherTypeException;
import org.prgrms.voucherapplication.view.io.Input;
import org.prgrms.voucherapplication.view.io.Menu;
import org.prgrms.voucherapplication.view.io.Output;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * 콘솔로 입출력하는 class
 */
@Component
public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    private static final String MENU_INPUT_TEXT = "=== Voucher Program ===\n"
            + "Type exit to exit the program.\n"
            + "Type create to create a new voucher.\n"
            + "Type list to list all vouchers.\n"
            + "Type blacklist to list blacklist.\n";
    private static final String VOUCHER_TYPE_INPUT_TEXT = "Type 1 to select fixed amount voucher.\n"
            + "Type 2 to select percent discount voucher.";
    private static final String VOUCHER_DISCOUNT_PERCENT_INPUT_TEXT = "Type discount rate."
            + "e.g., If you type \'10\', it means \'10%\'";
    private static final String VOUCHER_DISCOUNT_AMOUNT_INPUT_TEXT = "Type discount amount."
            + "e.g., If you type \'10\', it means \'$10\'";

    @Override
    public Menu inputMenu() throws InvalidMenuException {
        System.out.println(MENU_INPUT_TEXT);
        String input = scanner.nextLine();
        return Menu.getMenu(input);
    }

    @Override
    public VoucherType inputVoucherType() throws InvalidVoucherTypeException {
        System.out.println(VOUCHER_TYPE_INPUT_TEXT);
        String input = scanner.nextLine();
        return VoucherType.getVoucherType(input);
    }

    @Override
    public Long inputDiscount(VoucherType type) throws InputMismatchException {
        if (type.equals(VoucherType.FixedAmount)) {
            System.out.println(VOUCHER_DISCOUNT_AMOUNT_INPUT_TEXT);
        } else {
            System.out.println(VOUCHER_DISCOUNT_PERCENT_INPUT_TEXT);
        }

        String input = scanner.nextLine();
        return Long.valueOf(input);
    }

    @Override
    public void printVoucherList(List<Voucher> vouchers) {
        vouchers.stream().forEach(voucher -> System.out.println(voucher.toString()));
    }

    @Override
    public void printBlackList(List<Customer> customers) {
        customers.stream().forEach(customer -> System.out.println(customer.toString()));
    }

}
