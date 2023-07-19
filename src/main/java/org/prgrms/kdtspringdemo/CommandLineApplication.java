package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.constant.CustomerCommand;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommand;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
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

        VoucherResponseDto voucherResponseDto = voucherService.create(userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID);

        VoucherResponseDto voucherResponseDto = voucherService.findById(userVoucherId);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getAllVoucher() {
        List<VoucherResponseDto> vouchers = voucherService.findAll();
        for (VoucherResponseDto voucherResponseDto : vouchers) {
            voucherConsole.printVoucher(PRINT_VOUCHER_INFO, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
        }
    }

    private void updateVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID);
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER);

        VoucherResponseDto voucherResponseDto = voucherService.update(userVoucherId, userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
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

        CustomerResponseDto responseDto = customerService.create(userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, responseDto.getCustomerId(), responseDto.getNickname());
    }

    private void findByIdCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);

        CustomerResponseDto customerResponseDto = customerService.findById(userCustomerId);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void findByNicknameCustomer() {
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponseDto customerResponseDto = customerService.findByNickname(userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void findAllCustomer() {
        List<CustomerResponseDto> customers = customerService.findAll();
        for (CustomerResponseDto customerResponseDto : customers) {
            voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
        }
    }

    private void updateCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME);

        CustomerResponseDto customerResponseDto = customerService.update(userCustomerId, userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void deleteCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID);

        customerService.delete(userCustomerId);
    }
}
