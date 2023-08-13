package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.constant.CustomerCommand;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponse;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommand;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.Console;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.view.console.Message.*;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private final Console console = new Console();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = console.inputMainCommand(MAIN_PROGRAM_INIT);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = console.inputMainCommand(MAIN_PROGRAM_INIT);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> console.printMessage(SYSTEM_SHUTDOWN.getText());
            case VOUCHER -> runVoucherService();
            case CUSTOMER -> runCustomerService();
        }
    }

    private void runVoucherService() {
        VoucherCommand userCommand = console.inputVoucherCommand(VOUCHER_SERVICE_INIT);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case FIND_ID -> getVoucher();
            case FIND_ALL -> getAllVoucher();
            case UPDATE -> upsertVoucher();
            case DELETE -> deleteVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userType = console.chooseVoucherType(CHOICE_VOUCHER_TYPE);
        Long userAmount = console.inputAmountByVoucher(AMOUNT_VOUCHER);

        VoucherResponse response = voucherService.create(userType, userAmount);
        printVoucherResult(response);
    }

    private void printVoucherResult(VoucherResponse response) {
        console.printVoucher(PRINT_VOUCHER_INFO, response.id(), response.type(), response.amount());
    }

    private void getVoucher() {
        UUID userId = console.inputVoucherId(VOUCHER_ID);

        VoucherResponse response = voucherService.findById(userId);
        printVoucherResult(response);
    }

    private void getAllVoucher() {
        List<VoucherResponse> vouchers = voucherService.findAll();
        for (VoucherResponse response : vouchers) {
            printVoucherResult(response);
        }
    }

    private void upsertVoucher() {
        UUID userId = console.inputVoucherId(VOUCHER_ID);
        VoucherType userType = console.chooseVoucherType(CHOICE_VOUCHER_TYPE);
        Long userAmount = console.inputAmountByVoucher(AMOUNT_VOUCHER);

        VoucherResponse response = voucherService.upsert(userId, userType, userAmount);
        printVoucherResult(response);
    }

    private void deleteVoucher() {
        UUID userId = console.inputVoucherId(VOUCHER_ID);

        voucherService.delete(userId);
    }

    private void runCustomerService() {
        CustomerCommand userCommand = console.inputCustomerCommand(CUSTOMER_SERVICE_INIT);

        switch (userCommand) {
            case CREATE -> createCustomer();
            case FIND_ID -> findByIdCustomer();
            case FIND_NICKNAME -> findByNicknameCustomer();
            case FIND_ALL -> findAllCustomer();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
        }
    }

    private void createCustomer() {
        String userNickname = console.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse response = customerService.create(userNickname);
        printCustomerResult(response);
    }

    private void printCustomerResult(CustomerResponse customerResponse) {
        console.printCustomer(PRINT_CUSTOMER_INFO, customerResponse.id(), customerResponse.nickname());
    }

    private void findByIdCustomer() {
        UUID userId = console.inputCustomerId(CUSTOMER_ID);

        CustomerResponse response = customerService.findById(userId);
        printCustomerResult(response);
    }

    private void findByNicknameCustomer() {
        String userNickname = console.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse response = customerService.findByNickname(userNickname);
        printCustomerResult(response);
    }

    private void findAllCustomer() {
        List<CustomerResponse> customers = customerService.findAll();
        for (CustomerResponse response : customers) {
            printCustomerResult(response);
        }
    }

    private void updateCustomer() {
        UUID userId = console.inputCustomerId(CUSTOMER_ID);
        String userNickname = console.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse response = customerService.update(userId, userNickname);
        printCustomerResult(response);
    }

    private void deleteCustomer() {
        UUID userId = console.inputCustomerId(CUSTOMER_ID);

        customerService.delete(userId);
    }
}
