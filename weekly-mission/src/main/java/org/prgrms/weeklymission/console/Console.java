package org.prgrms.weeklymission.console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements Input, Output {
    @Override
    public String takeInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public String[] inputForCreateVoucher() {
        String option = takeInput();
        inputDiscountInfo(option);
        String discount = takeInput();

        return new String[]{option, discount};
    }

    @Override
    public void initMessage() {
        String sb = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type register to register black customer.
                Type blacks to list all black customers.
                """;

        System.out.println(sb);
    }

    public String[] inputForRegisterCustomer() {
        System.out.print("CustomerID: ");
        String customerId = takeInput();
        System.out.print("CustomerName: ");
        String customerName = takeInput();

        return new String[]{customerId, customerName};
    }

    @Override
    public void saveSuccessMessage() {
        System.out.println("saved successfully.\n");
    }

    @Override
    public void errorMessage(Exception e) {
        System.out.println("\n" + e.getMessage() + "\n");
    }

    @Override
    public void createVoucherMessage() {
        String sb = """
                
                1. FixedAmountVoucher
                2. PercentAmountVoucher
                """;

        System.out.println(sb);
    }

    @Override
    public void exitMessage() {
        System.out.println("The system is shutting down . . .");
    }

    @Override
    public void printData(String data) {
        System.out.println(data + "\n");
    }

    private void inputDiscountInfo(String option) {
        if(option.equals("1")) {
            System.out.print("Discount(amount): ");
        } else if(option.equals("2")) {
            System.out.print("Discount(percent): ");
        } else {
            System.out.print("Discount: ");
        }
    }
}
