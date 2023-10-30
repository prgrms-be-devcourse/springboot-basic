package org.prgms.springbootbasic.common.console;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collection;

import static org.prgms.springbootbasic.common.CommonConstant.INPUT_FIXED_AMOUNT_VOUCHER;
import static org.prgms.springbootbasic.common.CommonConstant.INPUT_PERCENT_DISCOUNT_VOUCHER;
import static org.prgms.springbootbasic.common.console.Console.consoleInput;

@Component
@Slf4j
public class MainConsole {

    public static String readCommand() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.println("Type 'black' to list customers blacked.");
        System.out.println("Type 'wallet' to enter wallet service.");

        return consoleInput.next();
    }

    public static int selectCreateType() {
        System.out.println();
        System.out.println("Which voucher would you like to create? Just type number.");
        System.out.println(MessageFormat.format("{0}. FixedAmountVoucher", INPUT_FIXED_AMOUNT_VOUCHER));
        System.out.println(MessageFormat.format("{0}. PercentDiscountVoucher", INPUT_PERCENT_DISCOUNT_VOUCHER));

        return consoleInput.nextInt();
    }

    public static int putDiscountDegree(VoucherType type) {
        switch (type){
            case FIXED_AMOUNT -> System.out.println("Enter the fixed discount amount.");
            case PERCENT_DISCOUNT -> System.out.println("Enter the discount percentage.");
        }
        return consoleInput.nextInt();
    }


    public static String ignoreLine() {
        return consoleInput.nextLine();
    }

    public static <T> void printList(Collection<T> collection) {
        System.out.println();
        collection.forEach(System.out::println);
        System.out.println();
    }

    public static void printArgException() {
        System.out.println("Invalid argument. Type command again.");
    }

    public static void printRuntimeException() {
        System.out.println("Invalid input or system error. redirect to beginning.");
    }
}
