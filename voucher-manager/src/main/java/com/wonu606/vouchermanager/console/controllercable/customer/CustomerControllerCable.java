package com.wonu606.vouchermanager.console.controllercable.customer;

import com.wonu606.vouchermanager.console.controllercable.customer.io.CustomerConsoleIo;
import com.wonu606.vouchermanager.controller.customer.CustomerController;
import com.wonu606.vouchermanager.controller.customer.request.CustomerCreateRequest;
import com.wonu606.vouchermanager.controller.customer.request.CustomerGetOwnedVouchersRequest;
import com.wonu606.vouchermanager.controller.customer.request.WalletDeleteRequest;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetOwnedVouchersResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerControllerCable {

    private final CustomerConsoleIo consoleIo;
    private final CustomerController controller;

    public CustomerControllerCable(CustomerConsoleIo consoleIo,
            CustomerController controller) {
        this.consoleIo = consoleIo;
        this.controller = controller;
    }

    public void run() {
        CustomerControllerMenu menu = CustomerControllerMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = consoleIo.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                consoleIo.displayMessage(exception.getMessage());
            }
        }
    }

    private void executeMenuAction(CustomerControllerMenu menu) {
        switch (menu) {
            case EXIT:
                return;
            case CREATE:
                CustomerCreateRequest customerCreateRequest = createCustomerCreateRequest();
                controller.createCustomer(customerCreateRequest);
                return;

            case LIST:
                CustomerGetResponse customerGetResponse = controller.getCustomerList();
                consoleIo.displayCustomerList(customerGetResponse);
                return;

            case VOUCHER_LIST:
                String searchedCustomerId = consoleIo.readString("CustomerId");
                CustomerGetOwnedVouchersResponse customerGetOwnedVouchersResponse = controller.getOwnedVouchersByCustomer(
                        new CustomerGetOwnedVouchersRequest(searchedCustomerId));
                consoleIo.displayVoucherList(customerGetOwnedVouchersResponse);
                return;

            case DELETE:
                String deletionCustomerId = consoleIo.readString(
                        "CustomerId To be Deleted");
                String deletionVoucherId = consoleIo.readString(
                        "VoucherId To be Deleted"
                );
                controller.deleteWallet(
                        new WalletDeleteRequest(deletionVoucherId, deletionCustomerId));
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private CustomerCreateRequest createCustomerCreateRequest() {
        String creationEmail = consoleIo.readString("Email");
        String creationNickName = consoleIo.readString("Nickname");
        return new CustomerCreateRequest(creationEmail, creationNickName);
    }
}
