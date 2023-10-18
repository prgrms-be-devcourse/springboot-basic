package com.zerozae.voucher.common;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.customer.CustomerController;
import com.zerozae.voucher.controller.voucher.VoucherController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.view.ConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class MenuHandler {

    private static final String MAIN_PROGRAM = "mainProgram";
    private static final String CUSTOMER_PROGRAM = "customerProgram";
    private static final String VOUCHER_PROGRAM = "voucherProgram";
    private final ConsoleView consoleView;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleView consoleView, VoucherController voucherController, CustomerController customerController) {
        this.consoleView = consoleView;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public MenuType selectCommand(String program){
        try {
            switch (program){
                case MAIN_PROGRAM -> consoleView.printCommand();
                case CUSTOMER_PROGRAM -> consoleView.printCustomerCommand();
                case VOUCHER_PROGRAM -> consoleView.printVoucherCommand();
            }
            return MenuType.of(consoleView.inputCommand());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void exit(){
        consoleView.printSystemMessage("프로그램을 종료합니다.");
    }

    public void createVoucher() {
        try {
            consoleView.printSystemMessage("바우처의 종류(Fixed / Percent)를 입력해주세요.");
            consoleView.printPrompt();
            VoucherType voucherType = VoucherType.of(consoleView.inputVoucherType());

            consoleView.printSystemMessage("바우처의 할인 정보를 입력해주세요.");
            consoleView.printPrompt();
            Long discount = consoleView.inputVoucherDiscount();

            VoucherRequest voucherRequest = new VoucherRequest(discount, voucherType);
            consoleView.printSystemMessage(voucherController.createVoucher(voucherRequest).getMessage());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void createCustomer(){
        try {
            consoleView.printSystemMessage("회원의 이름을 입력해주세요.");
            consoleView.printPrompt();
            String customerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage("회원 타입(NORMAL,BLACKLIST)을 입력해주세요.");
            consoleView.printPrompt();
            CustomerType customerType = CustomerType.of(consoleView.inputCustomerType());

            CustomerRequest customerRequest = new CustomerRequest(customerName, customerType);
            consoleView.printSystemMessage(customerController.createCustomer(customerRequest).getMessage());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void voucherList(){
        Response<List<VoucherResponse>> vouchers = voucherController.findAllVouchers();

        List<VoucherResponse> data = vouchers.getData();
        data.forEach(voucher -> consoleView.printInfo(voucher.getInfo()));
    }

    public void customerBlacklist() {
        Response<List<CustomerResponse>> blacklistCustomers = customerController.findAllBlacklistCustomers();

        List<CustomerResponse> data = blacklistCustomers.getData();
        data.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }


    public void customerList() {
        Response<List<CustomerResponse>> customers = customerController.findAllCustomers();

        List<CustomerResponse> data = customers.getData();
        data.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }
}
