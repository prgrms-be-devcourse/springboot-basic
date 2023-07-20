package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.constant.CustomerCommand;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponse;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommand;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponse;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.view.constant.ConsoleMessage.*;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN);
            case VOUCHER -> runVoucherService();
            case CUSTOMER -> runCustomerService();
        }
    }

    private void runVoucherService() {
        VoucherCommand userCommand = voucherConsole.inputVoucherCommand(VOUCHER_SERVICE_INIT);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case FIND_ID -> getVoucher();
            case FIND_ALL -> getAllVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER);

        VoucherResponse voucherResponse = voucherService.create(userVoucherType, userAmount);
        printVoucherResult(voucherResponse);
    }

    private void printVoucherResult(VoucherResponse voucherResponse) {
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO, voucherResponse.getVoucherId(), voucherResponse.getVoucherType(), voucherResponse.getAmount());
    }

    private void getVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID);

        VoucherResponse voucherResponse = voucherService.findById(userVoucherId);
        printVoucherResult(voucherResponse);
    }

    private void getAllVoucher() {
        List<VoucherResponse> vouchers = voucherService.findAll();
        for (VoucherResponse voucherResponse : vouchers) {
            printVoucherResult(voucherResponse);
        }
    }

    private void updateVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID);
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER);

        VoucherResponse voucherResponse = voucherService.update(userVoucherId, userVoucherType, userAmount);
        printVoucherResult(voucherResponse);
    }

    private void deleteVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID);

        voucherService.delete(userVoucherId);
    }

    private void runCustomerService() {
        CustomerCommand userCommand = voucherConsole.inputCustomerCommand(CUSTOMER_SERVICE_INIT);

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
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse customerResponse = customerService.create(userNickname);
        printCustomerResult(customerResponse);
    }

    private void printCustomerResult(CustomerResponse customerResponse) {
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, customerResponse.getCustomerId(), customerResponse.getNickname());
    }

    private void findByIdCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);

        CustomerResponse customerResponse = customerService.findById(userCustomerId);
        printCustomerResult(customerResponse);
    }

    private void findByNicknameCustomer() {
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse customerResponse = customerService.findByNickname(userNickname);
        printCustomerResult(customerResponse);
    }

    private void findAllCustomer() {
        List<CustomerResponse> customers = customerService.findAll();
        for (CustomerResponse customerResponse : customers) {
            printCustomerResult(customerResponse);
        }
    }

    private void updateCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponse customerResponse = customerService.update(userCustomerId, userNickname);
        printCustomerResult(customerResponse);
    }

    private void deleteCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);

        customerService.delete(userCustomerId);
    }
}
