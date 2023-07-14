package org.weekly.weekly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.weekly.weekly.customer.controller.CustomerController;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.customer.dto.response.CustomerResponse;
import org.weekly.weekly.customer.dto.response.CustomersResponse;
import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.util.CustomerMenu;
import org.weekly.weekly.util.ManageMenu;
import org.weekly.weekly.util.PrintMessageType;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.controller.VoucherController;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.dto.response.VouchersResponse;

@Component
public class VoucherManagementController {
    private final Logger logger = LoggerFactory.getLogger(VoucherManagementController.class);
    private final CommandLineApplication commandLineApplication;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherManagementController(CommandLineApplication commandLineApplication, VoucherController voucherController, CustomerController customerController) {
        this.commandLineApplication = commandLineApplication;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public void start() {
        boolean isRunning = true;

        while(isRunning) {
            try {
                ManageMenu manageMenu = commandLineApplication.readManageMenu();
                isRunning = processManageMenuSelection(manageMenu);
            } catch (RuntimeException runtimeException) {
                commandLineApplication.printErrorMsg(runtimeException.getMessage());
            }
        }
    }

    private boolean processManageMenuSelection(ManageMenu manageMenu) {
        return switch(manageMenu) {
            case VOUCHER -> {
                VoucherMenu voucherMenu = commandLineApplication.readVoucherMenu();
                processVoucherMenuSelection(voucherMenu);
                yield  false;
            }
            case CUSTOMER -> {
                CustomerMenu customerMenu = commandLineApplication.readCustomerMenu();
                processCustomerMenuSelection(customerMenu);
                yield  false;
            }
            default -> true;
        };
    }

    private void processVoucherMenuSelection(VoucherMenu selectMenu) {
        switch (selectMenu) {
            case CREATE -> handleVoucherCreation();
            case LIST -> handleVoucherSearch();
        }
    }

    private void handleVoucherCreation() {
        VoucherCreationRequest voucherCreationRequest = commandLineApplication.createVoucherFromInput();
        VoucherCreationResponse response = voucherController.createVoucher(voucherCreationRequest);
        logger.info("{}{}", PrintMessageType.CREATE_VOUCHER_SUCCESS.getMessage(),response.result());
        commandLineApplication.printResult(response);
    }

    private void handleVoucherSearch() {
        VouchersResponse response = voucherController.getVouchers();
        logger.info("{}{}", PrintMessageType.FIND_ALL_VOUCHER_SUCCESS.getMessage(), response.result());
        commandLineApplication.printResult(response);
    }

    private void processCustomerMenuSelection(CustomerMenu selectMenu) {
        switch(selectMenu) {
            case CREATE -> handleCustomerCreation();
            case DELETE -> handleCustomerDelete();
            case DELETE_ALL -> handleCustomerDeleteAll();
            case FIND_ALL -> handleCustomerFindAll();
            case FIND_DETAIL -> handleFindDetail();
            case UPDATE -> handleUpdateCustomer();
        }
    }

    private void handleCustomerCreation() {
        CustomerCreationRequest customerCreation = commandLineApplication.createCustomerFromInput();
        CustomerResponse customerDto = customerController.createCustomer(customerCreation);
        commandLineApplication.printResult(customerDto);
    }

    private void handleCustomerDelete() {
        CustomerUpdateRequest customerUpdateRequest = commandLineApplication.customerDetailFromInput();
        customerController.deleteCustomer(customerUpdateRequest);
        commandLineApplication.printDeleteMessage();
    }

    private void handleCustomerDeleteAll() {
        customerController.deleteAllCustomer();
        commandLineApplication.printDeleteMessage();
    }

    private void handleCustomerFindAll() {
        CustomersResponse customerResponses = customerController.findAllCustomer();
        commandLineApplication.printResult(customerResponses);
    }

    private void handleFindDetail() {
        CustomerUpdateRequest customerUpdateRequest = commandLineApplication.customerDetailFromInput();
        CustomerResponse customerResponse = customerController.findDetailCustomer(customerUpdateRequest);
        commandLineApplication.printResult(customerResponse);
    }

    private void handleUpdateCustomer() {
        CustomerUpdateRequest customerUpdateRequest = commandLineApplication.customerUpdateRequest();
        CustomerResponse customerResponse = customerController.updateCustomer(customerUpdateRequest);
        commandLineApplication.printResult(customerResponse);
    }

}
