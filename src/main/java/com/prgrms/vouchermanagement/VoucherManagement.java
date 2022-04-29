package com.prgrms.vouchermanagement;

import com.prgrms.vouchermanagement.command.MainCommand;
import com.prgrms.vouchermanagement.command.WalletCommand;
import com.prgrms.vouchermanagement.customer.BlackListRepository;
import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.io.Input;
import com.prgrms.vouchermanagement.io.Output;
import com.prgrms.vouchermanagement.voucher.Voucher;
import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import com.prgrms.vouchermanagement.wallet.VoucherWalletService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanagement.util.Messages.*;

@Component
public class VoucherManagement {

    private final VoucherService voucherService;
    private final VoucherWalletService walletService;
    private final BlackListRepository blackListRepository;
    private final CustomerService customerService;
    private final Input input;
    private final Output output;

    public VoucherManagement(VoucherService voucherService, VoucherWalletService walletService, BlackListRepository blackListRepository, CustomerService customerService, Input input, Output output) {
        this.voucherService = voucherService;
        this.walletService = walletService;
        this.blackListRepository = blackListRepository;
        this.customerService = customerService;
        this.input = input;
        this.output = output;
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            output.printMenu();
            String commandStr = input.inputCommand();

            //commandStr 문자열과 매칭되는 COMMAND 객체가 없으면 에러 메시지를 출력하고 다시 입력 받는다.
            if (canNotConvertCommandType(commandStr)) {
                printCommandError();
                continue;
            }

            MainCommand command = MainCommand.getCommand(commandStr);

            switch (command) {
                case CREATE_VOUCHER_COMMAND:
                    executeCreateVoucher();
                    break;
                case CREATE_CUSTOMER_COMMAND:
                    crateCustomer();
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
                case WALLET:
                    executeWalletProcess();
                    break;
                default:
                    printCommandError();
            }
        }
    }

    private boolean canNotConvertCommandType(String commandStr) {
        return !MainCommand.contain(commandStr);
    }

    private void crateCustomer() {
        String name = input.inputString("name");
        String email = input.inputString("email");

        try {
            customerService.addCustomer(name, email);
            output.printMessage("Customer is saved");
        } catch (IllegalArgumentException e) {
            output.printMessage("Duplicate email");
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void executeWalletProcess() {
        output.printWalletMenu();
        int menuOrder;

        try {
            menuOrder = input.inputNumber("wallet menu order");
        } catch (InputMismatchException e) {
            printCommandError();
            return;
        }

        if (!validateWalletMenuOrder(menuOrder)) {
            printCommandError();
            return;
        }

        WalletCommand command = WalletCommand.getWalletCommand(menuOrder);

        switch (command) {
            case ADD_VOUCHER:
                addVoucherToCustomerWallet();
                break;
            case FIND_VOUCHERS:
                findVoucherByCustomer();
                break;
            case REMOVE_VOUCHER:
                removeVoucherInWallet();
                break;
            case FIND_CUSTOMER:
                findCustomerByVoucher();
                break;
            default:
                printCommandError();
        }
    }

    /**
     * 입력 받은 월렛 메뉴 문자열이 숫자가 맞는지, WalletCommand에 포함되는지 확인한다.
     */
    private boolean validateWalletMenuOrder(int menuOrder) {
        return WalletCommand.contain(menuOrder);
    }

    private void removeVoucherInWallet() {
        try {
            UUID walletId = input.inputUUID("walletId");

            walletService.removeVoucherInWallet(walletId);
            output.printMessage("Voucher in this wallet is removed");
        } catch (InputMismatchException e) {
            output.printMessage("Please input in UUID format");
        } catch (IllegalArgumentException e) {
            output.printMessage("This wallet is not registered");
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void findCustomerByVoucher() {
        UUID voucherId = null;

        try {
            voucherId = input.inputUUID("voucherId");

            List<Customer> vouchers = walletService.findCustomerByVoucher(voucherId);

            if (vouchers == null || vouchers.isEmpty()) {
                output.printMessage("any customer has no this voucher");
                return;
            }

            output.printList(vouchers);
        } catch (InputMismatchException e) {
            output.printMessage("Please input in UUID format");
        } catch (IllegalArgumentException e) {
            output.printMessage("This voucher is not registered");
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void findVoucherByCustomer() {
        try {
            UUID customerId = input.inputUUID("customerId");

            if (!customerService.isRegisteredCustomer(customerId)) {
                output.printMessage("This customer is not registered");
                return;
            }

            List<Voucher> vouchers = voucherService.findVoucherByCustomer(customerId);

            if (vouchers == null || vouchers.isEmpty()) {
                output.printMessage(MessageFormat.format("{0} has no voucher", customerId));
                return;
            }

            output.printList(vouchers);

        } catch (InputMismatchException e) {
            output.printMessage("Please input in UUID format");
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void addVoucherToCustomerWallet() {
        try {
            UUID customerId = input.inputUUID("customerId");
            UUID voucherId = input.inputUUID("voucherId");

            walletService.addVoucherToWallet(customerId, voucherId);
            output.printMessage(MessageFormat.format("{0} is Saved to {1}''s wallet", voucherId, customerId));
        } catch (InputMismatchException e) {
            output.printMessage("Please input in UUID format");
        } catch (IllegalArgumentException e) {
            output.printMessage("Customer or voucher is not registered");
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void showBlackList() {
        List<Customer> blackList = blackListRepository.findAll();

        if (blackList == null || blackList.isEmpty()) {
            output.printMessage(BLACK_LIST_EMPTY);
        } else {
            output.printList(blackList);
        }
    }

    private void printCommandError() {
        output.printMessage(INPUT_ERROR);
    }

    private void showVoucherList() {
        try {
            List<Voucher> vouchers = voucherService.findAllVouchers();

            if (vouchers == null || vouchers.isEmpty()) {
                output.printMessage(VOUCHER_LIST_EMPTY);
                return;
            }

            output.printList(vouchers);
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }

    private void executeCreateVoucher() {
        try {
            VoucherType voucherType = VoucherType.getVoucherType(input.inputVoucherType());
            long discount = input.inputNumber("amount");

            voucherService.addVoucher(voucherType, discount);

            output.printMessage(SAVE_VOUCHER);
        } catch (IllegalArgumentException | InputMismatchException e) {
            output.printMessage(INPUT_ERROR);
        } catch (DataAccessException e) {
            output.printMessage(DB_ERROR_MESSAGE);
        }
    }
}
