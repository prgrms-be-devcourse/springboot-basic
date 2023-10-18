package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ConsolePrint {

    public void printFunctionSelect() {
        System.out.println(ConsoleMessage.SELECT_FUNCTION.getMessage());
    }

    public void printGetVoucherType() {
        System.out.println(ConsoleMessage.GET_VOUCHER_TYPE.getMessage());
    }
    public void printCompleteCreate() {
        System.out.println(ConsoleMessage.COMPLETE_CREATE_VOUCHER.getMessage());
        System.out.println();
    }

    public void printGetDiscountAmount() {
        System.out.println(ConsoleMessage.GET_DISCOUNT_AMOUNT.getMessage());
    }

    public void printGetDiscountPercent() {
        System.out.println(ConsoleMessage.GET_DISCOUNT_PERCENT.getMessage());
    }

    public void printList(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
                    System.out.println(voucher);
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
}
