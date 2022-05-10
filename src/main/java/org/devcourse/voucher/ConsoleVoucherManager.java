package org.devcourse.voucher;

import org.devcourse.voucher.voucher.controller.VoucherController;
import org.devcourse.voucher.customer.controller.CustomerController;
import org.devcourse.voucher.menu.model.CreateMenuType;
import org.devcourse.voucher.menu.model.MainMenuType;
import org.devcourse.voucher.menu.model.ListMenuType;
import org.devcourse.voucher.view.Input;
import org.devcourse.voucher.view.Output;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import static org.devcourse.voucher.error.ErrorType.*;
import static org.devcourse.voucher.menu.model.MainMenuType.*;


@Component
public class ConsoleVoucherManager {
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

    public void run() {
        MainMenuType command = NONE;
        while (command != EXIT) {
            output.mainMenu();
            command = discriminate(input.nextLine());
            switch(command) {
                case EXIT -> output.info("exit");
                case CREATE -> {
                    CreateMenuType createMenuType = selectCreate();
                    switch (createMenuType) {
                        case VOUCHER -> selectVoucher();
                        case CUSTOMER -> selectCustomer();
                        default -> output.warn(INVALID_COMMAND);
                    }
                }
                case LIST -> {
                    ListMenuType listMenuType = selectList();
                    output.printList(voucherController.findListMapper(listMenuType));
                }
                default -> output.warn(INVALID_COMMAND);
            }
        }
    }

    private void selectCustomer() {
        output.nameMenu();
        String name = input.nextLine();
        output.emailMenu();
        String email = input.nextLine();
        customerController.createCustomerMapper(name, email);
    }

    private void selectVoucher() {
        VoucherType voucherType = selectVoucherType();
        long discount = 0;
        while (discount <= 0) {
            discount = inputDiscount();
        }
        voucherController.createVoucherMapper(voucherType, discount);
    }

    private CreateMenuType selectCreate() {
        CreateMenuType createMenuType = CreateMenuType.NONE;
        while (createMenuType == CreateMenuType.NONE) {
            output.createMenu();
            createMenuType = CreateMenuType.discriminate(input.nextLine());
            if (createMenuType == CreateMenuType.NONE) {
                output.warn(INVALID_COMMAND);
            }
        }
        return createMenuType;
    }

    private ListMenuType selectList() {
        ListMenuType listMenuType = ListMenuType.NONE;
        while (listMenuType == ListMenuType.NONE) {
            output.listMenu();
            listMenuType = ListMenuType.discriminate(input.nextLine());
            if (listMenuType == ListMenuType.NONE) {
                output.warn(INVALID_COMMAND);
            }
        }
        return listMenuType;
    }

    private long inputDiscount() {
        output.discountMenu();
        String inputPrice = input.nextLine();
        long price;
        try {
            price = Long.parseLong(inputPrice);
            if (price <= 0) {
                output.warn(INPUT_NEGATIVE_NUMBERS);
            }
        } catch (NumberFormatException numberFormatException) {
            output.warn(INPUT_NOT_NUMBERS);
            return -1;
        }
        return price;
    }

    private VoucherType selectVoucherType() {
        VoucherType voucherType = VoucherType.NONE;
        while (voucherType == VoucherType.NONE) {
            output.voucherMenu();
            voucherType = VoucherType.optionDiscriminate(input.nextLine());
            if (voucherType == VoucherType.NONE) {
                output.warn(INVALID_COMMAND);
            }
        }
        return voucherType;
    }

}
