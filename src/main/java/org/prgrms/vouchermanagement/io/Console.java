package org.prgrms.vouchermanagement.io;

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
            "Type [delete] to delete customer's voucher." + System.lineSeparator() +
            "Type [create customer] to create a new customer." + System.lineSeparator() +
            "Type [show customers] to list all customer." + System.lineSeparator() +
            "Type [customer vouchers list] to list all vouchers the customer has" + System.lineSeparator() +
            "Type [voucher owner] to print the voucher owner" + System.lineSeparator() +
            "Type [blacklist] to list all blacklist";

    private static final String VOUCHER_CREATE_MESSAGE = "바우처 생성이 완료되었습니다.";
    private static final String CUSTOMER_NAME_INPUT_MESSAGE = "생성할 Customer의 이름을 입력해주세요.";
    private static final String CUSTOMER_EMAIL_INPUT_MESSAGE = "Customer의 이메일을 입력해주세요.";
    private static final String DELETE_VOUCHERS_MESSAGE = "바우처 삭제가 완료되었습니다.";
    private static final String VOUCHER_ID_INPUT_MESSAGE = "바우처 ID를 입력해주세요.";

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

        inputValidator.validateNumber(discountAmountInput);
        int discountAmount = Integer.parseInt(discountAmountInput);
        inputValidator.validateDiscountAmount(voucherTypeNumberInput, discountAmount);

        return discountAmount;
    }

    @Override
    public String receiveVoucherType() {
        this.printSelectVoucherType();
        String voucherType = scanner.nextLine();
        inputValidator.validateVoucherType(voucherType);
        return voucherType;
    }

    @Override
    public String receiveCustomerName() {
        this.printCustomerName();
        String customerName = scanner.nextLine();
        inputValidator.validateName(customerName);
        return customerName;
    }

    @Override
    public String receiveCustomerEmail() {
        this.printCustomerEmail();
        String customerEmail = scanner.nextLine();
        inputValidator.validateEmail(customerEmail);
        return customerEmail;
    }

    @Override
    public String receiveVoucherId() {
        this.printVoucherId();
        String voucherId = scanner.nextLine();
        inputValidator.validateUUID(voucherId);
        return voucherId;
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
    public void printList(List<?> list) {
        list.forEach(System.out::println);
    }

    @Override
    public void printCustomerName() {
        System.out.println(CUSTOMER_NAME_INPUT_MESSAGE);
    }

    @Override
    public void printCustomerEmail() {
        System.out.println(CUSTOMER_EMAIL_INPUT_MESSAGE);
    }

    @Override
    public void printDeleteVoucherMessage() {
        System.out.println(DELETE_VOUCHERS_MESSAGE);
    }

    @Override
    public void printVoucherId() {
        System.out.println(VOUCHER_ID_INPUT_MESSAGE);
    }
}
