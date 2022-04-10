package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.io.Input;
import com.prgrms.vouchermanagement.io.Output;
import com.prgrms.vouchermanagement.service.VoucherService;
import com.prgrms.vouchermanagement.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

import static com.prgrms.vouchermanagement.Messages.*;

@Component
public class VoucherManagement {

    private static final Logger log = LoggerFactory.getLogger(VoucherManagement.class);

    private final VoucherService voucherService;
    private final Input input;
    private final Output output;

    private final String CREATE_COMMAND = "create", LIST_COMMAND = "list", EXIT_COMMAND = "exit";

    public VoucherManagement(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    public void run() {

        while (true) {
            output.printMenu();
            String command = input.inputCommand();

            switch (command) {
                case CREATE_COMMAND:
                    executeCreateVoucher();
                    break;
                case LIST_COMMAND:
                    executeVoucherList();
                    break;
                case EXIT_COMMAND:
                    return;
                default:
                    printCommandError();
            }
        }
    }

    private void printCommandError() {
        output.printMessage(INPUT_ERROR);
    }

    private void executeVoucherList() {
        List<Voucher> vouchers = voucherService.findAllVouchers();

        if (vouchers == null || vouchers.isEmpty()) {
            output.printMessage(EMPTY_LIST);
            return;
        }

        output.printList(vouchers);
    }

    private void executeCreateVoucher() {
        int voucherOrder = 0;
        int discount = 0;
        Voucher voucher = null;

        try {
            voucherOrder = input.inputVoucherType();
            discount = input.inputDiscount();
            voucher = createVoucher(voucherOrder, discount);
        } catch (IllegalArgumentException | InputMismatchException e) {
            output.printMessage(INPUT_ERROR);
            return;
        }

        voucherService.addVoucher(voucher);
        output.printMessage(SAVE_VOUCHER);
    }

    private Voucher createVoucher(int voucherOrder, int discount) throws IllegalArgumentException {
        VoucherType voucherType = VoucherType.getVoucherType(voucherOrder);
        return Voucher.createVoucher(voucherType, discount);
    }
}
