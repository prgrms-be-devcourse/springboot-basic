package org.weekly.weekly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.weekly.weekly.customer.controller.CustomerController;
import org.weekly.weekly.customer.dto.request.CustomerCreationRequest;
import org.weekly.weekly.customer.dto.request.CustomerUpdateRequest;
import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.util.CustomerMenu;
import org.weekly.weekly.util.ManageMenu;
import org.weekly.weekly.util.PrintMessageType;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.controller.VoucherController;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;

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
        boolean isExit = false;

        while(!isExit) {
            try {
                ManageMenu manageMenu = this.commandLineApplication.readManageMenu();
                isExit = processManageMenuSelection(manageMenu);
            } catch (RuntimeException runtimeException) {
                this.commandLineApplication.printErrorMsg(runtimeException.getMessage());
            }
        }
    }

    private boolean processManageMenuSelection(ManageMenu manageMenu) {
        if (ManageMenu.VOUCHER.equals(manageMenu)) {
            VoucherMenu voucherMenu = this.commandLineApplication.readVoucherMenu();
            return processVoucherMenuSelection(voucherMenu);
        }

        if (ManageMenu.CUSTOMER.equals(manageMenu)){
            CustomerMenu customerMenu = this.commandLineApplication.readCustomerMenu();
            return processCustomerMenuSelection(customerMenu);
        }

        return true;
    }

    private boolean processVoucherMenuSelection(VoucherMenu selectMenu) {
        if (VoucherMenu.CREATE.equals(selectMenu)) {
            handleVoucherCreation();
            return false;
        }

        if (VoucherMenu.LIST.equals(selectMenu)) {
            handleVoucherSearch();
            return false;
        }

        return true;
    }

    private void handleVoucherCreation() {
        VoucherCreationRequest voucherCreationRequest = this.commandLineApplication.createVoucherFromInput();
        Response response = voucherController.createVoucher(voucherCreationRequest);
        logger.info("{}{}", PrintMessageType.CREATE_VOUCHER_SUCCESS.getMessage(),response.getResult());
        this.commandLineApplication.printResult(response);
    }

    private void handleVoucherSearch() {
        Response response = voucherController.getVouchers();
        logger.info("{}{}", PrintMessageType.FIND_ALL_VOUCHER_SUCCESS.getMessage(), response.getResult());
        this.commandLineApplication.printResult(response);
    }

    private boolean processCustomerMenuSelection(CustomerMenu selectMenu) {
        if (CustomerMenu.CREATE.equals(selectMenu)) {
            handleCustomerCreation();
            return false;
        }

        if (CustomerMenu.DELETE.equals(selectMenu)) {
            handleCustomerDelete();
            return false;
        }

        if (CustomerMenu.DELETE_ALL.equals(selectMenu)) {
            handleCustomerDeleteAll();
            return false;
        }

        if (CustomerMenu.SEARCH_ALL.equals(selectMenu)) {
            handleCustomerSearchAll();
            return false;
        }

        if (CustomerMenu.SEARCH_DETAIL.equals(selectMenu)) {
            handleSearchDetail();
            return false;
        }

        return true;
    }

    private void handleCustomerCreation() {
        CustomerCreationRequest customerCreation = this.commandLineApplication.createCustomerFromInput();
        this.customerController.createCustomer(customerCreation);
    }

    private void handleCustomerDelete() {
        CustomerUpdateRequest customerUpdateRequest = this.commandLineApplication.customerDetailFromInput();
        this.customerController.deleteCustomer(customerUpdateRequest);
    }

    private void handleCustomerDeleteAll() {
        this.customerController.deleteAllCustomer();
    }

    private void handleCustomerSearchAll() {
        this.customerController.searchAllCustomer();
    }

    private void handleSearchDetail() {
        CustomerUpdateRequest customerUpdateRequest = this.commandLineApplication.customerDetailFromInput();
        this.customerController.searchDetailCustomer(customerUpdateRequest);
    }

}
