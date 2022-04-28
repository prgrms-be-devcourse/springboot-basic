package org.devcourse.voucher;

import org.devcourse.voucher.controller.VoucherController;
import org.devcourse.voucher.model.Command;
import org.devcourse.voucher.model.ListType;
import org.devcourse.voucher.view.Input;
import org.devcourse.voucher.view.Output;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.springframework.stereotype.Component;

import static org.devcourse.voucher.error.ErrorType.*;
import static org.devcourse.voucher.model.Command.*;


@Component
public class ConsoleVoucherManager {
    VoucherController voucherController;
    Input input;
    Output output;

    public ConsoleVoucherManager(VoucherController voucherController, Input input, Output output) {
        this.voucherController = voucherController;
        this.input = input;
        this.output = output;
    }

    public void run() {
        Command command = NONE;
        while (command != EXIT) {
            output.mainMenu();
            command = discriminate(input.nextLine());
            switch(command) {
                case EXIT -> output.info("bye");
                case CREATE -> {
                    VoucherType voucherType = selectVoucher();
                    long discount = 0;
                    while (discount <= 0) {
                        discount = inputDiscount();
                    }
                    voucherController.createVoucherMapper(voucherType, discount);
                }
                case LIST -> {
                    ListType listType = selectList();
                    output.printList(voucherController.findListMapper(listType));
                }
                default -> output.warn(INVALID_COMMAND);
            }
        }
    }

    private ListType selectList() {
        ListType listType = ListType.NONE;
        while (listType == ListType.NONE) {
            output.listMenu();
            listType = ListType.discriminate(input.nextLine());
            if (listType == ListType.NONE) {
                output.warn(INVALID_COMMAND);
            }
        }
        return listType;
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

    private VoucherType selectVoucher() {
        VoucherType voucherType = VoucherType.NONE;
        while (voucherType == VoucherType.NONE) {
            output.createMenu();
            voucherType = VoucherType.discriminate(input.nextLine());
            if (voucherType == VoucherType.NONE) {
                output.warn(INVALID_COMMAND);
            }
        }
        return voucherType;
    }

}
