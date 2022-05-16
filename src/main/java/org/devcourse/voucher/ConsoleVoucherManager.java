package org.devcourse.voucher;

import org.devcourse.voucher.error.ErrorType;
import org.devcourse.voucher.voucher.controller.VoucherController;
import org.devcourse.voucher.customer.controller.CustomerController;
import org.devcourse.voucher.menu.model.CreateMenuType;
import org.devcourse.voucher.menu.model.MainMenuType;
import org.devcourse.voucher.menu.model.ListMenuType;
import org.devcourse.voucher.view.console.Input;
import org.devcourse.voucher.view.console.Output;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ConsoleVoucherManager implements CommandLineRunner {
    private static final int INVALID_COMMAND = -1;
    private static final int MIN_PRICE = 0;

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final Input input;
    private final Output output;

    public ConsoleVoucherManager(VoucherController voucherController, CustomerController customerController, Input input, Output output) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run(String... args) throws Exception {
        MainMenuType menu;

        do {
            output.mainMenu();
            menu = mainMenu(MainMenuType.discriminate(input.nextLine()));
        } while (menu != MainMenuType.EXIT);
    }

    MainMenuType mainMenu(MainMenuType command) {
        switch (command) {
            case EXIT -> output.info("exit");
            case CREATE -> selectCreate();
            case LIST -> selectList();
            default -> output.error(ErrorType.INVALID_COMMAND);
        }
        return command;
    }

    private void selectCustomer() {
        output.nameMenu();
        String name = input.nextLine();
        output.emailMenu();
        String email = input.nextLine();
        customerController.postCreateCustomer(name, email);
    }

    private void selectVoucher() {
        VoucherType voucherType = selectVoucherType();
        long discount = 0;
        while (discount <= 0) {
            discount = inputDiscount();
        }
        voucherController.postCreateVoucher(voucherType, discount);
    }

    private void selectCreate() {
        CreateMenuType command;
        boolean isSelected = false;
        while (!isSelected) {
            output.createMenu();
            try {
                command = CreateMenuType.discriminate(input.nextLine());
                switch (command) {
                    case VOUCHER -> selectVoucher();
                    case CUSTOMER -> selectCustomer();
                    default -> output.error(ErrorType.INVALID_COMMAND);
                }
                isSelected = true;
            } catch (IllegalArgumentException e) {
                output.error(ErrorType.INVALID_COMMAND);
            }
        }
    }

    private void selectList() {
        ListMenuType command;
        boolean isSelected = false;

        while (!isSelected) {
            output.listMenu();
            try {
                command = ListMenuType.discriminate(input.nextLine());
                switch (command) {
                    case VOUCHER -> output.printList(voucherController.getVoucherList());
                    case CUSTOMER -> output.printList(customerController.getCustomerList());
                    case BLACKLIST -> output.printList(customerController.getBlackList());
                    default -> output.error(ErrorType.INVALID_COMMAND);
                }
                isSelected = true;
            } catch (IllegalArgumentException e) {
                output.error(ErrorType.INVALID_COMMAND);
            }
        }
    }

    private long inputDiscount() {
        output.discountMenu();
        String inputPrice = input.nextLine();
        long price;
        try {
            price = Long.parseLong(inputPrice);
            if (price <= MIN_PRICE) {
                output.error(ErrorType.INPUT_NEGATIVE_NUMBERS);
            }
        } catch (NumberFormatException numberFormatException) {
            output.error(ErrorType.INPUT_NOT_NUMBERS);
            return INVALID_COMMAND;
        }
        return price;
    }

    private VoucherType selectVoucherType() {
        VoucherType command = null;
        boolean isSelected = false;

        while (!isSelected) {
            output.voucherMenu();
            try {
                command = VoucherType.optionDiscriminate(input.nextLine());
                isSelected = true;
            } catch (IllegalArgumentException e) {
                output.error(ErrorType.INVALID_TYPE);
            }
        }
        return command;
    }
}
