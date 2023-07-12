package com.wonu606.vouchermanager.consoleInterface;

import com.wonu606.vouchermanager.controller.CustomerController;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.customer.CustomerDto;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.io.CustomerConsoleIO;
import com.wonu606.vouchermanager.menu.CustomerMenu;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsoleInterface {

    private final CustomerConsoleIO customerConsoleIO;
    private final CustomerController customerController;

    public CustomerConsoleInterface(CustomerConsoleIO customerConsoleIO,
            CustomerController customerController) {
        this.customerConsoleIO = customerConsoleIO;
        this.customerController = customerController;
    }

    public void run() {
        CustomerMenu menu = CustomerMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = customerConsoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                customerConsoleIO.displayMessage(exception.getMessage());
            }
        }
    }

    private void executeMenuAction(CustomerMenu menu) {
        switch (menu) {
            case EXIT:
                return;
            case CREATE:
                CustomerDto customerDtoToCreate = createCustomerDto();
                customerController.createCustomer(customerDtoToCreate);
                return;

            case LIST:
                List<Customer> customerList = customerController.getCustomerList();
                customerConsoleIO.displayCustomerList(customerList);
                return;

            case VOUCHER_LIST:
                String emailAddress = customerConsoleIO.readString("Email Address");
                List<Voucher> voucherListOwned =
                        customerController.getVouchersOwnedByCustomer(emailAddress);
                customerConsoleIO.displayVoucherList(voucherListOwned);
                return;

            case DELETE:
                String emailAddressToDelete = customerConsoleIO.readString(
                        "Email Address To be Deleted");
                String voucherIdToDelete = customerConsoleIO.readString(
                        "Voucher UUID To be Deleted"
                );
                customerController.deleteWallet(emailAddressToDelete, voucherIdToDelete);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private CustomerDto createCustomerDto() {
        String emailAddress = customerConsoleIO.readString("Email Address");
        String nickName = customerConsoleIO.readString("Nickname");
        return new CustomerDto(emailAddress, nickName);
    }
}
