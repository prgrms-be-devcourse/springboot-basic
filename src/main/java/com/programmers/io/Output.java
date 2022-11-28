package com.programmers.io;

import com.programmers.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Output {

    public void printDescription() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type exit to exit the program.");
    }
    public void printStartOrder() {
        System.out.println("Type order");
    }
    public void printTermination() {
        System.out.println("Exit application");
    }


    public void printSelectVoucher() {
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.println("Type type to select voucher");
    }

    public void printSelectDiscount(String guideMessage) {
        System.out.println(guideMessage);
    }

    public void printStorage(List<Voucher> history) {
        history.forEach((v) -> System.out.println(v.toString()));
    }

    public void printError(String errorMessage) {
        System.out.println(errorMessage);
    }
}
