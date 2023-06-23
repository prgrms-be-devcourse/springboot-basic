package com.programmers.springweekly.view;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Console implements Input, Output {

    private final Scanner scanner = new Scanner(System.in);

    private final String PROGRAM_TITLE = "=== Voucher Program ===";
    private final String PROGRAM_EXIT_GUIDE = "Type exit to exit the program.";
    private final String PROGRAM_CREATE_GUIDE = "Type create to create a new voucher.";
    private final String PROGRAM_LIST_GUIDE = "Type list to list all vouchers.";
    private final String PROGRAM_BLACKLIST_GUIDE = "Type blacklist to blacklist all customer blacklist";
    private final String VOUCHER_SELECT_GUIDE = "Select fixed or percent.";
    private final String VOUCHER_DISCOUNT_GUIDE = "If fixed, enter freely, and if percentage, enter a number between 1 and 100.";
    private final String GAME_END_EXIT_MESSAGE = "Program has ended.";

    @Override
    public String inputMessage() {
        return scanner.nextLine();
    }

    @Override
    public void outputProgramGuide() {
        System.out.println(PROGRAM_TITLE);
        System.out.println(PROGRAM_EXIT_GUIDE);
        System.out.println(PROGRAM_CREATE_GUIDE);
        System.out.println(PROGRAM_LIST_GUIDE);
        System.out.println(PROGRAM_BLACKLIST_GUIDE);
    }

    @Override
    public void outputSelectCreateVoucherGuide() {
        System.out.println(VOUCHER_SELECT_GUIDE);
    }

    @Override
    public void outputDiscountGuide() {
        System.out.println(VOUCHER_DISCOUNT_GUIDE);
    }

    @Override
    public void outputExitMessage() {
        System.out.println(GAME_END_EXIT_MESSAGE);
    }

    @Override
    public void outputGetVoucherAll(Map<UUID, Voucher> voucherMap) {
        for(UUID voucherId : voucherMap.keySet()){
            System.out.println("=========================================================");
            System.out.println("voucherId : " + voucherMap.get(voucherId).getVoucherId());
            System.out.println("discountAmount : " + voucherMap.get(voucherId).getVoucherAmount());
            System.out.println("voucherType : " + voucherMap.get(voucherId).getVoucherType());
            System.out.println("=========================================================\n");
        }
    }

    @Override
    public void outputGetCustomerBlackList(Map<UUID, Customer> customerMap) {
        for(UUID customerId : customerMap.keySet()){
            System.out.println("=========================================================");
            System.out.println("customerId : " + customerMap.get(customerId).getCustomerId());
            System.out.println("customerType : " + customerMap.get(customerId).getCustomerType());
            System.out.println("=========================================================\n");
        }
    }

    @Override
    public void outputErrorMessage(String errorText) {
        System.out.println(errorText);
    }
}
