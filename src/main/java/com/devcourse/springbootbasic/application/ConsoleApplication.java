package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.customer.controller.CustomerController;
import com.devcourse.springbootbasic.application.customer.controller.CustomerDto;
import com.devcourse.springbootbasic.application.global.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.global.io.ConsoleManager;
import com.devcourse.springbootbasic.application.global.model.CommandMenu;
import com.devcourse.springbootbasic.application.global.model.DomainMenu;
import com.devcourse.springbootbasic.application.global.model.PropertyMenu;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherController;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    private final ConsoleManager consoleManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public ConsoleApplication(
            ConsoleManager consoleManager,
            VoucherController voucherController,
            CustomerController customerController
    ) {
        this.consoleManager = consoleManager;
        this.voucherController = voucherController;
        this.customerController = customerController;
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

    private boolean branchByMenu(CommandMenu menu) {
        switch (menu) {
            case EXIT -> {
                consoleManager.consoleClosePlatform();
                return true;
            }
            case CREATE -> createTask(consoleManager.consoleDomainMenu());
            case UPDATE -> updateTask(consoleManager.consoleDomainMenu());
            case REMOVE -> removeTask(consoleManager.consoleDomainMenu(), menu);
            case LIST -> listTask(consoleManager.consoleDomainMenu(), menu);
        }
        return false;
    }

    private void createTask(DomainMenu domainMenu) {
        var consoleId = false;
        switch (domainMenu) {
            case VOUCHER -> {
                var createdVoucherDto = voucherController.createVoucher(consoleManager.getVoucherDto(consoleId));
                consoleManager.printVoucherDto(createdVoucherDto);
            }
            case CUSTOMER -> {
                var registedCustomerDto = customerController.registerCustomer(consoleManager.getCustomerDto(consoleId));
                consoleManager.printCustomerDto(registedCustomerDto);
            }
        }
    }

    private void updateTask(DomainMenu domainMenu) {
        var consoleId = true;
        switch (domainMenu) {
            case VOUCHER -> {
                var updatedVoucherDto = voucherController.updateVoucher(consoleManager.getVoucherDto(consoleId));
                consoleManager.printVoucherDto(updatedVoucherDto);
            }
            case CUSTOMER -> {
                var updateCustomerDto = customerController.updateCustomer(consoleManager.getCustomerDto(consoleId));
                consoleManager.printCustomerDto(updateCustomerDto);
            }
        }
    }

    private void removeTask(DomainMenu domainMenu, CommandMenu commandMenu) {
        var filterActive = domainMenu == DomainMenu.CUSTOMER && commandMenu == CommandMenu.LIST;
        switch (domainMenu) {
            case VOUCHER -> removeVoucherTask(consoleManager.consoleProperty(filterActive));
            case CUSTOMER -> removeCustomerTask(consoleManager.consoleProperty(filterActive));
        }
    }

    private void removeVoucherTask(PropertyMenu propertyMenu) {
        switch (propertyMenu) {
            case ALL -> voucherController.deleteVouchers();
            case ID -> voucherController.deleteVoucherById(consoleManager.consoleId());
        }
    }

    private void removeCustomerTask(PropertyMenu propertyMenu) {
        switch (propertyMenu) {
            case ALL -> customerController.deleteAllCustomers();
            case ID -> customerController.deleteCustomerById(consoleManager.consoleId());
        }
    }

    private void listTask(DomainMenu domainMenu, CommandMenu commandMenu) {
        var filterActive = domainMenu == DomainMenu.CUSTOMER && commandMenu == CommandMenu.LIST;
        switch (domainMenu) {
            case VOUCHER -> listVoucherTask(consoleManager.consoleProperty(filterActive));
            case CUSTOMER -> listCustomerTask(consoleManager.consoleProperty(filterActive));
        }
    }

    private void listVoucherTask(PropertyMenu propertyMenu) {
        List<VoucherDto> list = switch (propertyMenu) {
            case ALL -> voucherController.findAllVouchers();
            case ID -> List.of(voucherController.findVoucherById(consoleManager.consoleId()));
            case BLACK_CUSTOMER -> List.of();
        };
        consoleManager.printVoucherList(list);
    }

    private void listCustomerTask(PropertyMenu propertyMenu) {
        List<CustomerDto> list = switch (propertyMenu) {
            case ALL -> customerController.findAllCustomers();
            case BLACK_CUSTOMER -> customerController.findBlackCustomers();
            case ID -> List.of(customerController.findCustomerById(consoleManager.consoleId()));
        };
        consoleManager.printCustomerList(list);
    }

}
