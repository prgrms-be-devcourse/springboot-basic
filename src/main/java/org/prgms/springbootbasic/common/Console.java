package org.prgms.springbootbasic.common;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class Console { // view - domain 분리 필. Controller를 따로 만들고, 거기서 View, Service 호출.
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in);

    public static String readCommand(){
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.println("Type 'black' to list customers blacked.");

        return CONSOLE_INPUT.next();
    }

    public static int selectCreateType(){
        System.out.println();
        System.out.println("Which voucher would you like to create? Just type number.");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");

        return CONSOLE_INPUT.nextInt();
    }

    public static int putDiscountDegree(VoucherType type){
        switch (type){
            case FIXED_AMOUNT -> System.out.println("Enter the fixed discount amount.");
            case PERCENT_DISCOUNT -> System.out.println("Enter the discount percentage.");
        }
        return CONSOLE_INPUT.nextInt();
    }


    public static String ignoreLine(){
        return CONSOLE_INPUT.nextLine();
    }

    public static <T> void printList(Collection<T> collection){
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
