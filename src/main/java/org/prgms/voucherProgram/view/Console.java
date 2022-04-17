package org.prgms.voucherProgram.view;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
    private static final String PROMPT = "> ";
    private static final String REQUEST_INPUT_CONSOLE_COMMAND = "\n=== Console Program ===\nType \"exit\" to exit the program.\nType \"voucher\" run Voucher program.\nType \"customer\" run Customer program.";
    private static final String REQUEST_INPUT_VOUCHER_COMMAND = "\n=== Voucher Program ===\nType \"exit\" to exit the Voucher program.\nType \"create\" to create a new voucher.\nType \"list\" to list all vouchers.";
    private static final String REQUEST_INPUT_CUSTOMER_COMMAND = "\n=== Customer Program ===\nType \"exit\" to exit the Customer program.\nType \"create\" to create a new customer.\nType \"read\" to read customers.\nType \"update\" to update customer.\nType \"delete\" to delete customer.";
    private static final String REQUEST_INPUT_VOUCHER_TYPE = "\nSelect a voucher type\nType \"1\" to create a new FixedAmountVoucher\nType \"2\" to create a new PercentDiscountVoucher";
    private static final String REQUEST_INPUT_CUSTOMER_SUB_COMMAND = "\nSelect a range\nType \"all\" to do ALL\nType \"one\" to do just one (need email input)\nType \"blacklist\" to list all black customer.";
    private static final String REQUEST_INPUT_DISCOUNT_AMOUNT = "\nInput voucher discount amount : ";
    private static final String REQUEST_INPUT_DISCOUNT_PERCENTAGE = "\nInput voucher discount percentage : ";
    private static final String EMPTY_VOUCHERS = "Empty Vouchers";
    private static final String EMPTY_CUSTOMERS = "Empty Customers";
    private static final String ERROR_INPUT_NUMBER_TYPE = "[ERROR] 정수만 입력가능합니다.";

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputConsoleMenu() {
        System.out.println(REQUEST_INPUT_CONSOLE_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public String inputVoucherMenu() {
        System.out.println(REQUEST_INPUT_VOUCHER_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public String inputCustomerMenu() {
        System.out.println(REQUEST_INPUT_CUSTOMER_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public String inputCustomerSubMenu() {
        System.out.println(REQUEST_INPUT_CUSTOMER_SUB_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public String inputVoucherCommand() {
        System.out.println(REQUEST_INPUT_VOUCHER_TYPE);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public CustomerDto inputCustomerInformation() {
        String name = inputCustomerName();
        String email = inputCustomerEmail();
        return new CustomerDto(UUID.randomUUID(), name, email);
    }

    @Override
    public String inputCustomerName() {
        System.out.print("\nname : ");
        return scanner.nextLine().trim();
    }

    @Override
    public String inputCustomerEmail() {
        System.out.print("email : ");
        return scanner.nextLine().trim();
    }

    @Override
    public long inputDiscountValue(VoucherType voucherType) {
        String message = REQUEST_INPUT_DISCOUNT_PERCENTAGE;

        if (voucherType == VoucherType.FIXED_AMOUNT) {
            message = REQUEST_INPUT_DISCOUNT_AMOUNT;
        }

        System.out.print(message);
        return convertToLong(scanner.nextLine().trim());
    }

    private Long convertToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INPUT_NUMBER_TYPE);
        }
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.printf("%s\n%n", voucher);
    }

    @Override
    public void printCustomer(CustomerDto customerDto) {
        System.out.printf("%s\n%n", customerDto);
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_VOUCHERS);
            return;
        }

        System.out.println();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
        System.out.println();
    }

    @Override
    public void printCustomers(List<CustomerDto> customers) {
        if (customers.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_CUSTOMERS);
            return;
        }

        System.out.println();
        for (CustomerDto customerDto : customers) {
            System.out.println(customerDto);
        }
        System.out.println();
    }

    @Override
    public void printBlackList(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_CUSTOMERS);
            return;
        }

        System.out.println();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();
    }

    @Override
    public void printSuccess() {
        System.out.println("Success!");
    }

    @Override
    public void printError(String message) {
        System.out.println("\n" + message + "\n");
    }
}
