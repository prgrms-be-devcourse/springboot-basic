package com.tangerine.voucher_system.application;

import com.tangerine.voucher_system.application.customer.controller.CustomerController;
import com.tangerine.voucher_system.application.customer.controller.CustomerDto;
import com.tangerine.voucher_system.application.customer.controller.CustomerMenu;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.global.io.ConsoleManager;
import com.tangerine.voucher_system.application.global.model.CommandMenu;
import com.tangerine.voucher_system.application.voucher.controller.VoucherController;
import com.tangerine.voucher_system.application.voucher.controller.VoucherDto;
import com.tangerine.voucher_system.application.voucher.controller.VoucherMenu;
import com.tangerine.voucher_system.application.wallet.controller.WalletController;
import com.tangerine.voucher_system.application.wallet.controller.WalletMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    private final ConsoleManager consoleManager;
    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final WalletController walletController;

    public ConsoleApplication(
            ConsoleManager consoleManager,
            CustomerController customerController,
            VoucherController voucherController,
            WalletController walletController
    ) {
        this.consoleManager = consoleManager;
        this.customerController = customerController;
        this.voucherController = voucherController;
        this.walletController = walletController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                CommandMenu menu = consoleManager.consoleCommandMenu();
                if (branchByMenu(menu)) {
                    break;
                }
            } catch (InvalidDataException exception) {
                logger.error(exception.getMessage(), exception.getCause());
                consoleManager.consoleError(exception);
            }
        }
    }

    private boolean branchByMenu(CommandMenu commandMenu) {
        switch (commandMenu) {
            case EXIT -> {
                consoleManager.consoleClosePlatform();
                return true;
            }
            case CUSTOMER -> customerTask();
            case VOUCHER -> voucherTask();
            case WALLET -> walletTask();
        }
        return false;
    }

    private void customerTask() {
        CustomerMenu customerMenu = consoleManager.consoleCustomerMenu();
        switch (customerMenu) {
            case REGISTER -> {
                CustomerDto customerDto = customerController.registerCustomer(consoleManager.consoleCustomerDto());
                consoleManager.printCustomerDto(customerDto);
            }
            case UPDATE -> {
                CustomerDto customerDto = customerController.updateCustomer(consoleManager.consoleCustomerDto());
                consoleManager.printCustomerDto(customerDto);
            }
            case UNREGISTER -> {
                CustomerDto customerDto = customerController.unregisterCustomerById(consoleManager.consoleId());
                consoleManager.printCustomerDto(customerDto);
            }
            case LIST_ALL -> {
                List<CustomerDto> customerDtoList = customerController.customerList();
                consoleManager.printCustomerList(customerDtoList);
            }
            case LIST_BLACK -> {
                List<CustomerDto> customerDtoList = customerController.blackCustomerList();
                consoleManager.printCustomerList(customerDtoList);
            }
            case LIST_BY_ID -> {
                CustomerDto customerDto = customerController.customerById(consoleManager.consoleId());
                consoleManager.printCustomerDto(customerDto);
            }
            case LIST_BY_NAME -> {
                CustomerDto customerDto = customerController.customerByName(consoleManager.consoleName());
                consoleManager.printCustomerDto(customerDto);
            }
        }
    }

    private void voucherTask() {
        VoucherMenu voucherMenu = consoleManager.consoleVoucherMenu();
        switch (voucherMenu) {
            case CREATE -> {
                VoucherDto voucherDto = voucherController.createVoucher(consoleManager.consoleVoucherDto());
                consoleManager.printVoucherDto(voucherDto);
            }
            case UPDATE -> {
                VoucherDto voucherDto = voucherController.updateVoucher(consoleManager.consoleVoucherDto());
                consoleManager.printVoucherDto(voucherDto);
            }
            case DELETE -> {
                VoucherDto voucherDto = voucherController.deleteVoucherById(consoleManager.consoleId());
                consoleManager.printVoucherDto(voucherDto);
            }
            case LIST_ALL -> {
                List<VoucherDto> voucherDtoList = voucherController.voucherList();
                consoleManager.printVoucherList(voucherDtoList);
            }
            case LIST_BY_ID -> {
                VoucherDto voucherDto = voucherController.voucherById(consoleManager.consoleId());
                consoleManager.printVoucherDto(voucherDto);
            }
            case LIST_BY_TYPE -> {
                VoucherDto voucherDto = voucherController.voucherByType(consoleManager.consoleVoucherType());
                consoleManager.printVoucherDto(voucherDto);
            }
        }
    }

    private void walletTask() {
        WalletMenu walletMenu = consoleManager.consoleWalletMenu();
        switch (walletMenu) {
            case ASSIGN_VOUCHER_TO_CUSTOMER ->
                    walletController.assignVoucherToCustomer(consoleManager.consoleId(), consoleManager.consoleId());
            case WITHDRAW_VOUCHER_FROM_CUSTOMER ->
                    walletController.withdrawVoucherFromCustomer(consoleManager.consoleId());
            case LIST_VOUCHERS_OF_CUSTOMER -> {
                List<VoucherDto> voucherDtoList = walletController.voucherListOfCustomer(consoleManager.consoleId());
                consoleManager.printVoucherList(voucherDtoList);
            }
            case LIST_CUSTOMER_BY_VOUCHER_ID -> {
                CustomerDto customerDto = walletController.customerHasVoucher(consoleManager.consoleId());
                consoleManager.printCustomerDto(customerDto);
            }
            case LIST_CUSTOMER_BY_VOUCHER_TYPE -> {
                List<CustomerDto> customerDtoList = walletController.customerListHasVoucherType(consoleManager.consoleVoucherType());
                consoleManager.printCustomerList(customerDtoList);
            }
        }
    }

}
