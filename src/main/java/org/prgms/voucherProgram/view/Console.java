package org.prgms.voucherProgram.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
    public static final String REQUEST_UPDATE_DISCOUNT_VALUE = "Enter voucher discount value to update : ";
    private static final String PROMPT = "> ";
    private static final String REQUEST_INPUT_CONSOLE_COMMAND = "\n=== Console Program ===\nType \"exit\" to exit the program.\nType \"voucher\" run Voucher program.\nType \"customer\" run Customer program.";
    private static final String REQUEST_INPUT_VOUCHER_COMMAND = "\n=== Voucher Program ===\nType \"exit\" to exit the Voucher program.\nType \"create\" to create a new voucher.\nType \"list\" to list all vouchers.\nType \"update\" to update voucher.\nType \"delete\" to delete voucher.";
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
    public int inputVoucherType() {
        return convertToInt();
    }

    @Override
    public VoucherDto inputVoucherInformation(int voucherType) {
        long discountValue = inputDiscountValue(voucherType);
        return new VoucherDto(UUID.randomUUID(), voucherType, discountValue);
    }

    private long inputDiscountValue(int voucherType) {
        String message = REQUEST_INPUT_DISCOUNT_PERCENTAGE;
        if (voucherType == 1) {
            message = REQUEST_INPUT_DISCOUNT_AMOUNT;
        }
        System.out.print(message);
        return convertToLong(message);
    }

    @Override
    public CustomerDto inputCustomerInformation() {
        String name = inputCustomerName();
        String email = inputCustomerEmail();
        return new CustomerDto(UUID.randomUUID(), name, email, null, LocalDateTime.now());
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
    public Long inputDiscountPercent() {
        System.out.print(REQUEST_INPUT_DISCOUNT_PERCENTAGE);
        return convertToLong(REQUEST_INPUT_DISCOUNT_PERCENTAGE);
    }

    @Override
    public Long inputDiscountAmount() {
        System.out.print(REQUEST_INPUT_DISCOUNT_AMOUNT);
        return convertToLong(REQUEST_INPUT_DISCOUNT_PERCENTAGE);
    }

    @Override
    public VoucherDto inputUpdateVoucher() {
        UUID voucherId = inputVoucherId();
        int voucherType = inputVoucherType();
        System.out.print(REQUEST_UPDATE_DISCOUNT_VALUE);
        Long discountValue = convertToLong(REQUEST_UPDATE_DISCOUNT_VALUE);
        return new VoucherDto(voucherId, voucherType, discountValue);
    }

    @Override
    public UUID inputVoucherId() {
        while (true) {
            try {
                System.out.print("Enter voucherID to do : ");
                return UUID.fromString(scanner.nextLine().trim());
            } catch (IllegalArgumentException e) {
                printError("[ERROR] UUID 형식이 아닙니다.");
            }
        }
    }

    private int convertToInt() {
        while (true) {
            try {
                System.out.println(REQUEST_INPUT_VOUCHER_TYPE);
                System.out.print(PROMPT);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError(ERROR_INPUT_NUMBER_TYPE);
            }
        }
    }

    private Long convertToLong(String requestMessage) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError(ERROR_INPUT_NUMBER_TYPE);
                System.out.println(requestMessage);
            }
        }
    }

    @Override
    public void printVoucher(VoucherDto voucherDto) {
        System.out.printf("%s\n%n", voucherDto);
    }

    @Override
    public void printCustomer(CustomerDto customerDto) {
        System.out.printf("%s\n%n", customerDto);
    }

    @Override
    public void printVouchers(List<VoucherDto> vouchers) {
        if (vouchers.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_VOUCHERS);
            return;
        }

        System.out.println();
        for (VoucherDto voucherDto : vouchers) {
            System.out.println(voucherDto);
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
