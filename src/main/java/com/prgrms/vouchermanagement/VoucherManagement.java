package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.io.Input;
import com.prgrms.vouchermanagement.io.Output;
import com.prgrms.vouchermanagement.customer.BlackListRepository;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
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

    private final String CREATE_COMMAND = "create", LIST_COMMAND = "list", EXIT_COMMAND = "exit", BLACK_LIST_COMMAND = "blacklist";

    public VoucherManagement(VoucherService voucherService, BlackListRepository blackListRepository, Input input, Output output) {
        this.voucherService = voucherService;
        this.blackListRepository = blackListRepository;
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
                    showVoucherList();
                    break;
                case EXIT_COMMAND:
                    return;
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

    /**
     * 입력 받은 voucher 번호와 할인액을 입력 받아 Voucher 인스턴스를 생성하고 반환한다.
     */
    private Voucher createVoucher(int voucherOrder, int discount) throws IllegalArgumentException {
        VoucherType voucherType = VoucherType.getVoucherType(voucherOrder);
        return Voucher.createVoucher(voucherType, discount);
    }
}
