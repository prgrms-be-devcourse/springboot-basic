package com.programmers.io;

import com.programmers.TypeOfVoucher;
import com.programmers.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Output {

    public void printDescription() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type exit to exit the program.");
    }

    public void printTermination() {
        System.out.println("Exit application");
    }

    public void printStartOrder() {
        System.out.println("Type order");
    }

    public void printSelectVoucher() {
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.println("Type number to select voucher");
    }

    public void printSelectDiscount(TypeOfVoucher typeOfVoucher) {
        switch (typeOfVoucher) {
            case FIXED_AMOUNT_VOUCHER -> System.out.println("Type fixed amount");
            case PERCENT_DISCOUNT_VOUCHER -> System.out.println("Type percent discount");
        }
    }

    public void printStorage(Map<UUID, Voucher> history) {
        history.forEach((key, value) -> System.out.println("ID -> " + key + " Type -> " + value.getType() + " Discount -> " + value.getDiscount()));

    }
}
