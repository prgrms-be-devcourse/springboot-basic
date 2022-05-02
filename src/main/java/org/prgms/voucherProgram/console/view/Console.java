package org.prgms.voucherProgram.console.view;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.prgms.voucherProgram.customer.domain.Customer;
import org.prgms.voucherProgram.customer.dto.CustomerRequest;
import org.prgms.voucherProgram.voucher.domain.Voucher;
import org.prgms.voucherProgram.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.wallet.dto.WalletRequest;
import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
    private static final String PROMPT = "> ";
    private static final String REQUEST_INPUT_CONSOLE_COMMAND = "\n=== Console Program ===\nType \"exit\" to exit the program.\nType \"voucher\" run Voucher program.\nType \"customer\" run Customer program.";
    private static final String REQUEST_INPUT_VOUCHER_COMMAND = "\n=== Voucher Program ===\nType \"exit\" to exit the Voucher program.\nType \"create\" to create a new voucher.\nType \"list\" to list all vouchers.\nType \"update\" to update voucher.\nType \"delete\" to delete voucher.\nType \"wallet\" to wallet voucher.";
    private static final String REQUEST_INPUT_CUSTOMER_COMMAND = "\n=== Customer Program ===\nType \"exit\" to exit the Customer program.\nType \"create\" to create a new customer.\nType \"read\" to read customers.\nType \"update\" to update customer.\nType \"delete\" to delete customer.";
    private static final String REQUEST_INPUT_WALLET_COMMAND = "\n=== WALLET Program ===\nType \"exit\" to exit the WALLET program.\nType \"assign\" voucher assign to customer\nType \"list\" to list customer has vouchers.\nType \"delete\" to delete customer has voucher.\nType \"find\" find customer with voucher.";
    private static final String REQUEST_INPUT_VOUCHER_TYPE = "\nSelect a voucher type\nType \"1\" to create a new FixedAmountVoucher\nType \"2\" to create a new PercentDiscountVoucher";
    private static final String REQUEST_INPUT_CUSTOMER_SUB_COMMAND = "\nSelect a range\nType \"all\" to do ALL\nType \"one\" to do just one (need email input)\nType \"blacklist\" to list all black customer.";
    private static final String REQUEST_INPUT_DISCOUNT_VALUE = "\nInput voucher discount value : ";
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
    public CustomerRequest inputCustomerInformation() {
        String name = inputCustomerName();
        String email = inputCustomerEmail();
        return new CustomerRequest(name, email);
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
    public int inputVoucherType() {
        return convertToInt();
    }

    @Override
    public VoucherRequest inputVoucherInformation(int voucherType) {
        long discountValue = inputDiscountValue();
        return new VoucherRequest(voucherType, discountValue);
    }

    @Override
    public long inputDiscountValue() {
        System.out.print(REQUEST_INPUT_DISCOUNT_VALUE);
        return convertToLong();
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

    @Override
    public String inputWalletMenu() {
        System.out.println(REQUEST_INPUT_WALLET_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim();
    }

    @Override
    public WalletRequest inputWalletInformation() {
        String email = inputCustomerEmail();
        UUID voucherId = inputVoucherId();
        return new WalletRequest(email, voucherId);
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

    private Long convertToLong() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError(ERROR_INPUT_NUMBER_TYPE);
            }
        }
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.printf("%s\n%n", voucher);
    }

    @Override
    public void printCustomer(Customer customer) {
        System.out.printf("%s\n%n", customer);
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
    public void printCustomers(List<Customer> customers) {
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
