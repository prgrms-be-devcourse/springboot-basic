package org.programmers.springorder.console;

import org.programmers.springorder.constant.ErrorMessage;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.dto.customer.CustomerRequestDto;
import org.programmers.springorder.dto.customer.CustomerResponseDto;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.dto.voucher.VoucherResponseDto;
import org.programmers.springorder.dto.wallet.WalletRequestDto;
import org.programmers.springorder.model.voucher.VoucherType;
import org.programmers.springorder.utils.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printPrompt() {
        System.out.print("> ");
    }

    public static MenuType inputMenu() {
        printMessage(Message.MENU_SELECT_MESSAGE);
        printPrompt();
        return MenuType.selectMenu(scanner.nextLine());
    }

    /**
     * Voucher
     */
    public static VoucherMenuType inputVoucherMenu() {
        printMessage(Message.VOUCHER_MENU_SELECT_MESSAGE);
        printPrompt();
        return VoucherMenuType.selectVoucherMenu(scanner.nextLine());
    }

    private VoucherType inputVoucherType() {
        printMessage(Message.VOUCHER_SELECT_MESSAGE);
        printPrompt();
        return VoucherType.selectVoucherType(scanner.nextLine());
    }

    private long inputVoucherValue(VoucherType voucherType) {
        String discountValueMessage = voucherType == VoucherType.FIXED ? Message.INPUT_FIXED_DISCOUNT_VALUE_MESSAGE
                : Message.INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE;
        printMessage(discountValueMessage);
        printPrompt();

        long discountValue = Long.parseLong(scanner.nextLine());
        voucherType.validateDiscountRange(voucherType, discountValue);
        return discountValue;
    }

    public VoucherRequestDto inputVoucherInfo() {
        VoucherType voucherType = ExceptionHandler.input(this::inputVoucherType);
        long discountValue = ExceptionHandler.input(() -> inputVoucherValue(voucherType));
        return new VoucherRequestDto(discountValue, voucherType);
    }

    public void showVoucherList(List<VoucherResponseDto> allVoucher) {
        if (allVoucher.isEmpty()) {
            printMessage(ErrorMessage.VOUCHER_NOT_EXIST_MESSAGE);
        } else {
            allVoucher.forEach(System.out::println);
        }
    }

    public String inputVoucherId() {
        printMessage(Message.INPUT_VOUCHER_ID);
        printPrompt();
        return scanner.nextLine();
    }

    /**
     * Customer
     */
    public static CustomerMenuType inputCustomerMenu() {
        printMessage(Message.CUSTOMER_MENU_SELECT_MESSAGE);
        printPrompt();
        return CustomerMenuType.selectCustomerMenu(scanner.nextLine());
    }

    private String inputCustomerName() {
        printMessage(Message.INPUT_CUSTOMER_NAME_MESSAGE);
        printPrompt();

        String name = scanner.nextLine();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_VALUE_MESSAGE);
        }
        return name;
    }

    public CustomerRequestDto inputCustomerInfo() {
        String name = ExceptionHandler.input(this::inputCustomerName);
        return new CustomerRequestDto(name);
    }

    public void showBlackList(List<CustomerResponseDto> blackList) {
        if (blackList.isEmpty()) {
            printMessage(ErrorMessage.BLACK_CONSUMER_NOT_EXIST_MESSAGE);
        } else {
            blackList.forEach(System.out::println);
        }
    }

    /**
     * Wallet
     */
    public static WalletMenuType inputWalletMenu() {
        printMessage(Message.WALLET_MENU_SELECT_MESSAGE);
        printPrompt();
        return WalletMenuType.selectWalletMenu(scanner.nextLine());
    }

    public String inputCustomerId() {
        printMessage(Message.INPUT_CUSTOMER_ID_MESSAGE);
        printPrompt();
        return scanner.nextLine();
    }

    public WalletRequestDto inputWalletInfo() {
        UUID customerId = UUID.fromString(ExceptionHandler.input(this::inputCustomerId));
        UUID voucherId = UUID.fromString(ExceptionHandler.input(this::inputVoucherId));
        return new WalletRequestDto(customerId, voucherId);
    }
}
