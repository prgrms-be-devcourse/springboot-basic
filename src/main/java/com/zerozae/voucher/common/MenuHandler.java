package com.zerozae.voucher.common;

import com.zerozae.voucher.controller.customer.CustomerController;
import com.zerozae.voucher.controller.voucher.VoucherController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.view.ConsoleView;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

@Component
public class MenuHandler {
    private final ConsoleView consoleView;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleView consoleView, VoucherController voucherController, CustomerController customerController) {
        this.consoleView = consoleView;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public MenuType showAndSelectCommand(){
        consoleView.printCommand();
        try {
            return MenuType.of(consoleView.inputCommand());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public MenuType selectedCustomerProgram(){
        consoleView.printCustomerCommand();
        try {
            return MenuType.of(consoleView.inputCommand());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public MenuType selectedVoucherProgram(){
        consoleView.printVoucherCommand();
        try {
            return MenuType.of(consoleView.inputCommand());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void exit(){
        consoleView.printSystemMessage(getMessage("EXIT_PROGRAM.MSG"));
    }

    public void createVoucher() {
        try {
            consoleView.printSystemMessage(getMessage("INPUT_VOUCHER_TYPE.MSG"));
            consoleView.printPrompt();
            VoucherType voucherType = VoucherType.of(consoleView.inputVoucherType());

            consoleView.printSystemMessage(getMessage("INPUT_VOUCHER_DISCOUNT.MSG"));
            consoleView.printPrompt();
            Long discount = consoleView.inputVoucherDiscount();

            VoucherRequest voucherRequest = new VoucherRequest(discount, voucherType);
            consoleView.printSystemMessage(voucherController.createVoucher(voucherRequest).getMessage());
        }catch (ErrorMessage e){
            consoleView.printErrorMessage(e.getMessage());
        }
    }

    public void createCustomer(){
        try {
            consoleView.printSystemMessage(getMessage("INPUT_CUSTOMER_NAME.MSG"));
            consoleView.printPrompt();
            String customerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage(getMessage("INPUT_CUSTOMER_TYPE.MSG"));
            consoleView.printPrompt();
            CustomerType customerType = CustomerType.of(consoleView.inputCustomerType());

            CustomerRequest customerRequest = new CustomerRequest(customerName, customerType);
            consoleView.printSystemMessage(customerController.createCustomer(customerRequest).getMessage());
        }catch (ErrorMessage e){
            consoleView.printErrorMessage(e.getMessage());
        }
    }

    public void voucherList(){
        List<String> vouchers = voucherController.findAllVouchers().getData();
        vouchers.forEach(consoleView::printInfo);
    }

    public void customerBlacklist() {
        List<String> blackCustomers = customerController.findAllBlacklistCustomers().getData();
        blackCustomers.forEach(consoleView::printInfo);
    }

    public void customerList() {
        List<String> customers = customerController.findAllCustomers().getData();
        customers.forEach(consoleView::printInfo);
    }
}
