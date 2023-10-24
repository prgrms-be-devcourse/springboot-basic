package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.prgrms.vouchermanager.message.ConsoleMessage.*;

@Component
@Slf4j
public class ConsolePrint {

    public void printVoucherFunctionSelect() {
        System.out.println(SELECT_FUNCTION_VOUCHER.getMessage());
    }

    public void printGetVoucherType() {
        System.out.println(GET_VOUCHER_TYPE.getMessage());
    }
    public void printCompleteCreate() {
        System.out.println(COMPLETE_CREATE_VOUCHER.getMessage());
        System.out.println();
    }

    public void printGetDiscountAmount() {
        System.out.println(GET_DISCOUNT_AMOUNT.getMessage());
    }

    public void printGetDiscountPercent() {
        System.out.println(GET_DISCOUNT_PERCENT.getMessage());
    }

    public void printVoucherList(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
                    System.out.println(voucher);
                    System.out.println("---------------");
                });
        System.out.println();
    }

    public void printCustomerList(List<Customer> customers) {
         customers.forEach(customer -> {
            System.out.println(customer);
            System.out.println("---------------");
        });
        System.out.println();
    }

    public void printBlacklist(List<Customer> blacklist) {
        blacklist.forEach(customer -> {
            System.out.println(customer);
            System.out.println("---------------");
        });
        System.out.println();
    }

    public void printProgramSelect() {
        System.out.println(SELECT_PROGRAM.getMessage());
    }

    public void printGetCustomerName() {
        System.out.println(GET_CUSTOMER_NAME.getMessage());
    }

    public void printGetCustomerYear() {
        System.out.println(GET_CUSTOMER_YEAR.getMessage());
    }

    public void printCustomerFunctionSelect() {
        System.out.println(SELECT_FUNCTION_CUSTOMER.getMessage());
    }

    public void printUpdateSelect() {
        System.out.println(SELECT_UPDATE_TARGET.getMessage());
    }

    public void printCompleteUpdate() {
        System.out.println(COMPLETE_UPDATE_CUSTOMER.getMessage());
    }

    public void printGetCustomerId() {
        System.out.println(GET_CUSTOMER_ID.getMessage());
    }

    public void printCompleteDelete() {
        System.out.println(COMPLETE_DELETE_CUSTOMER.getMessage());
    }

    public void printWalletFunctionSelect() {
        System.out.println(SELECT_FUNCTION_WALLET.getMessage());
    }

    public void printGetVoucherId() {
        System.out.println(GET_VOUCHER_ID.getMessage());
    }
}
