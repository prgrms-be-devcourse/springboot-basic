package org.prgms.springbootbasic.common.console;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.INPUT_FIXED_AMOUNT_VOUCHER;
import static org.prgms.springbootbasic.common.CommonConstant.INPUT_PERCENT_DISCOUNT_VOUCHER;

@Component
@Slf4j
public class Console { // Console이 common인가? MVC 생각하며 고민하자. View에 해당한다고 보이는데... MVC가 뭔지 대답 못한 것을 두번째 걸렸다. 공부하자...
    public static final Scanner consoleInput = new Scanner(System.in);

    public static String readCommand() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'createVoucher' to create a new voucher.");
        System.out.println("Type 'createCustomer' to create a new customer.");
        System.out.println("Type 'listVoucher' to list all vouchers.");
        System.out.println("Type 'listCustomer' to list all customers.");
        System.out.println("Type 'black' to list customers blacked.");
        System.out.println("Type 'wallet' to enter wallet service.");
        System.out.println("Type 'exit' to exit the program.");

        return consoleInput.next();
    }

    public static int selectPolicyType() {
        System.out.println();
        System.out.println("Which voucher would you like to create? Just type number.");
        System.out.println(MessageFormat.format("{0}. with fixed amount discount policy", INPUT_FIXED_AMOUNT_VOUCHER));
        System.out.println(MessageFormat.format("{0}. with percent discount policy", INPUT_PERCENT_DISCOUNT_VOUCHER));

        return consoleInput.nextInt();
    }

    public static String putCustomerInfo() {
        ignoreLine();

        System.out.println();
        System.out.println("Please enter the name and email of the customer you want to create, separated by a space on a single line.");

        return consoleInput.nextLine();
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

    public static void printArgException() {
        System.out.println("Invalid argument. Type command again.");
    }

    public static void printRuntimeException() {
        System.out.println("Invalid input or system error. redirect to beginning.");
    }

    public static String readWalletCommand() {
        System.out.println();
        System.out.println("Welcome to our wallet service.");
        System.out.println("Type 'allocate' if you want to allocate voucher to a customer." );
        System.out.println("Type 'delete' if you want to delete voucher from a customer.");
        System.out.println("Type 'showVoucherByCustomer' to view customers with a voucher.");
        System.out.println("Type 'showCustomerByVoucher' to view vouchers that a customer has.");
        System.out.println("Type 'back' to go back.");

        return consoleInput.next();
    }

    public static UUID typeCustomerId(){
        System.out.println("Type customer Id.");

        return UUID.fromString(consoleInput.next());
    }

    public static UUID typeVoucherId(){
        System.out.println("Type voucher Id.");

        return UUID.fromString(consoleInput.next());
    }

    public static void success(String command){
        System.out.println("'" + command + "' command successfully executed.");
        System.out.println();
    }

    public static <T> void printList(Collection<T> collection) {
        System.out.println();
        collection.forEach(System.out::println);
        System.out.println();
    }
}
