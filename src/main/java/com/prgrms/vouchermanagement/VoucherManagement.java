package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.customer.BlackListRepository;
import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.io.Input;
import com.prgrms.vouchermanagement.io.Output;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;

import static com.prgrms.vouchermanagement.util.Messages.*;

@Component
public class VoucherManagement {

    private final VoucherService voucherService;
    private final BlackListRepository blackListRepository;
    private final Input input;
    private final Output output;

    public VoucherManagement(VoucherService voucherService, BlackListRepository blackListRepository, Input input, Output output) {
        this.voucherService = voucherService;
        this.blackListRepository = blackListRepository;
        this.input = input;
        this.output = output;
    }
    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            output.printMenu();
            String commandStr = input.inputCommand();

            //commandStr 문자열과 매칭되는 COMMAND 객체가 없으면 에러 메시지를 출력하고 다시 입력 받는다.
            if (!COMMAND.contain(commandStr)) {
                printCommandError();
                continue;
            }

            COMMAND command = COMMAND.getCommand(commandStr);

            switch (command) {
                case CREATE_COMMAND:
                    executeCreateVoucher();
                    break;
                case LIST_COMMAND:
                    showVoucherList();
                    break;
                case EXIT_COMMAND:
                    isRunning = false;
                    break;
                case BLACK_LIST_COMMAND:
                    showBlackList();
                    break;
                default:
                    printCommandError();
            }
        }
    }

    private void showBlackList() {
        List<Customer> blackList = blackListRepository.findAll();

        if (blackList == null || blackList.isEmpty()) {
            output.printMessage(BLACK_LIST_EMPTY);
        } else {
            output.printBlackList(blackList);
        }
    }

    private void printCommandError() {
        output.printMessage(INPUT_ERROR);
    }

    private void showVoucherList() {
        List<Voucher> vouchers = voucherService.findAllVouchers();

        if (vouchers == null || vouchers.isEmpty()) {
            output.printMessage(VOUCHER_LIST_EMPTY);
            return;
        }

        output.printVoucherList(vouchers);
    }

    private void executeCreateVoucher() {
        try {
            VoucherType voucherType = VoucherType.getVoucherType(input.inputVoucherType());
            long discount = input.inputDiscount();

            voucherService.addVoucher(voucherType, discount);

            output.printMessage(SAVE_VOUCHER);
        } catch (IllegalArgumentException | InputMismatchException e) {
            output.printMessage(INPUT_ERROR);
        }
    }
}
