package org.programmers.springorder.console;

import org.programmers.springorder.consts.ErrorMessage;
import org.programmers.springorder.consts.Message;
import org.programmers.springorder.customer.dto.CustomerResponseDto;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.voucher.dto.VoucherRequestDto;
import org.programmers.springorder.voucher.dto.VoucherResponseDto;
import org.programmers.springorder.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);    // TODO: logger 사용하지 않으면 제거
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

    private static VoucherType inputVoucherType() {
        printMessage(Message.VOUCHER_SELECT_MESSAGE);
        printPrompt();
        return VoucherType.selectVoucherType(scanner.nextLine());
    }

    private static long inputVoucherValue(VoucherType voucherType) {
        String discountValueMessage = voucherType == VoucherType.FIXED ? Message.INPUT_FIXED_DISCOUNT_VALUE_MESSAGE
                : Message.INPUT_PERCENT_DISCOUNT_VALUE_MESSAGE;
        printMessage(discountValueMessage);
        printPrompt();
        return scanner.nextLong();
    }


    public static VoucherRequestDto inputVoucherInfo() {
        VoucherType voucherType = inputVoucherType();
        long discountValue = inputVoucherValue(voucherType);

        return new VoucherRequestDto(discountValue, voucherType);
    }

    public void showList(List<VoucherResponseDto> allVoucher) {
        if (allVoucher.isEmpty()) {
            printMessage(ErrorMessage.VOUCHER_NOT_EXIST_MESSAGE);
        } else {
            allVoucher.forEach(System.out::println);
        }
    }

    public void showBlackList(List<CustomerResponseDto> blackList) {
        if (blackList.isEmpty()) {
            printMessage(ErrorMessage.BLACK_CONSUMER_NOT_EXIST_MESSAGE);
        } else {
            blackList.forEach(System.out::println);
        }
    }
}
