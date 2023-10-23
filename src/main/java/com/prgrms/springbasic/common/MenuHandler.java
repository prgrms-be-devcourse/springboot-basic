package com.prgrms.springbasic.common;

import com.prgrms.springbasic.domain.customer.controller.CustomerController;
import com.prgrms.springbasic.domain.customer.dto.CreateCustomerRequest;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.voucher.controller.VoucherController;
import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.io.Console;
import com.prgrms.springbasic.io.ConsoleMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuHandler {
    private final Console console;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(Console console, VoucherController voucherController, CustomerController customerController) {
        this.console = console;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public String chooseMode() {
        console.printConsoleMessage(ConsoleMessage.START_VOUCHER_PROGRAM);
        return console.inputMenuType();
    }

    public boolean exit() {
        console.printConsoleMessage(ConsoleMessage.EXIT_PROGRAM);
        return false;
    }

    public void createVoucher() {
        console.printConsoleMessage(ConsoleMessage.CREATE_VOUCHER);
        voucherController.saveVoucher(makeCreateVoucherRequest());
    }

    public void showAllVouchers() {
        List<VoucherResponse> vouchers = voucherController.findAll();
        console.printVouchers(vouchers);
    }

    public void createCustomer(){
        console.printConsoleMessage(ConsoleMessage.CREATE_CUSTOMER);
        String email = console.inputEmail();
        String name = console.inputString(ConsoleMessage.GET_NAME);
        customerController.createCustomer(new CreateCustomerRequest(email, name));
    }

    public void showAllBlackLists() {
        List<CustomerResponse> customers = customerController.findAllBlackLists();
        console.printCustomers(customers);
    }

    private CreateVoucherRequest makeCreateVoucherRequest() {
        String discountType = console.inputDiscountType();
        long discountValue = switch (DiscountType.find(discountType)) {
            case PERCENT -> console.inputPercentValue();
            case FIXED -> console.inputLong(ConsoleMessage.GET_FIXED_DISCOUNT_VALUE);
        };
        return new CreateVoucherRequest(discountType, discountValue);
    }
}
