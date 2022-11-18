package org.prgrms.vouchermanagement.io;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
@Component
public class Console implements Input, Output {

    public static final String VOUCHER_TYPE_CHOICE = System.lineSeparator() +
            "=== Select voucher type ===" + System.lineSeparator() +
            "1 : Fixed Amount Voucher" + System.lineSeparator() +
            "2 : Percent Discount Voucher";

    public static final String DISCOUNT_AMOUNT_INPUT = "=== Enter the discount amount ===";

    public static final String COMMAND_NOTICE = System.lineSeparator() +
            "=== Voucher Program ===" + System.lineSeparator()
            + "Type [exit] to exit the program" + System.lineSeparator() +
            "Type [create] to create a new voucher." + System.lineSeparator() +
            "Type [list] to list all vouchers." + System.lineSeparator() +
            "Type [create customer] to create a new customer." + System.lineSeparator() +
            "Type [show customers] to list all customer." + System.lineSeparator() +
            "Type [blacklist] to list all blacklist";

    public static final String VOUCHER_CREATE_MESSAGE = "바우처 생성이 완료되었습니다.";
    private static final String CUSTOMER_NAME_INPUT_MESSAGE = "생성할 Customer의 이름을 입력해주세요.";
    private static final String CUSTOMER_EMAIL_INPUT_MESSAGE = "생성할 Customer의 이메일을 입력해주세요.";
    public static final String VOUCHER_ASSIGN_CUSTOMER_EMAIL_MESSAGE = "바우처를 할당하기 위한 고객의 이메일을 입력해주세요.";

    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator inputValidator;

    public Console(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    // INPUT
    @Override
    public String receiveCommand() {
        return scanner.nextLine();
    }

    @Override
    public int receiveDiscountAmount(String voucherTypeNumberInput) {
        this.printSelectVoucherDiscountAmount();
        String discountAmountInput = scanner.nextLine();

        int discountAmount = inputValidator.validateNumber(discountAmountInput);
        return inputValidator.validateDiscountAmount(voucherTypeNumberInput, discountAmount);
    }

    @Override
    public String receiveVoucherType() {
        this.printSelectVoucherType();
        return inputValidator.validateVoucherType(scanner.nextLine());
    }

    @Override
    public String receiveCustomerName() {
        this.printCustomerName();
        return inputValidator.validateName(scanner.nextLine());
    }

    @Override
    public String receiveCustomerEmail() {
        this.printCustomerEmail();
        return inputValidator.validateEmail(scanner.nextLine());
    }

    // OUTPUT
    @Override
    public void printCommandNotices() {
        System.out.println(COMMAND_NOTICE);
    }

    @Override
    public void printSelectVoucherType() {
        System.out.println(VOUCHER_TYPE_CHOICE);
    }

    @Override
    public void printSelectVoucherDiscountAmount() {
        System.out.println(DISCOUNT_AMOUNT_INPUT);
    }

    @Override
    public void printVoucherCreateMessage() {
        System.out.println(VOUCHER_CREATE_MESSAGE);
    }

    @Override
    public void printAllVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    @Override
    public void printCustomers(List<Customer> customers) {
        customers.forEach(System.out::println);
    }

    @Override
    public void printVoucherAssignCustomerEmail() {
        System.out.println(VOUCHER_ASSIGN_CUSTOMER_EMAIL_MESSAGE);
    }

    @Override
    public void printCustomerName() {
        System.out.println(CUSTOMER_NAME_INPUT_MESSAGE);
    }

    @Override
    public void printCustomerEmail() {
        System.out.println(CUSTOMER_EMAIL_INPUT_MESSAGE);
    }
}
