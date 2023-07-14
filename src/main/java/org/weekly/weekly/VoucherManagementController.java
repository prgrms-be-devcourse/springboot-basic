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
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;

import java.util.List;

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
                ManageMenu manageMenu = commandLineApplication.readManageMenu();
                isExit = processManageMenuSelection(manageMenu);
            } catch (RuntimeException runtimeException) {
                commandLineApplication.printErrorMsg(runtimeException.getMessage());
            }
        }
    }

    private boolean processManageMenuSelection(ManageMenu manageMenu) {
        if (ManageMenu.EXIT.equals(manageMenu)) {
            return true;
        }

        if (ManageMenu.VOUCHER.equals(manageMenu)) {
            VoucherMenu voucherMenu = commandLineApplication.readVoucherMenu();
            processVoucherMenuSelection(voucherMenu);
            return false;
        }

        if (ManageMenu.CUSTOMER.equals(manageMenu)){
            CustomerMenu customerMenu = commandLineApplication.readCustomerMenu();
            processCustomerMenuSelection(customerMenu);
            return false;
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
        VoucherCreationRequest voucherCreationRequest = commandLineApplication.createVoucherFromInput();
        Response response = voucherController.createVoucher(voucherCreationRequest);
        logger.info("{}{}", PrintMessageType.CREATE_VOUCHER_SUCCESS.getMessage(),response.getResult());
        commandLineApplication.printResult(response);
    }

    private void handleVoucherSearch() {
        Response response = voucherController.getVouchers();
        logger.info("{}{}", PrintMessageType.FIND_ALL_VOUCHER_SUCCESS.getMessage(), response.getResult());
        commandLineApplication.printResult(response);
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

        if (CustomerMenu.FIND_ALL.equals(selectMenu)) {
            handleCustomerFindAll();
            return false;
        }

        if (CustomerMenu.FIND_DETAIL.equals(selectMenu)) {
            handleFindDetail();
            return false;
        }

        if (CustomerMenu.UPDATE.equals(selectMenu)) {
            handleUpdateCustomer();
            return false;
        }

        return true;
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
