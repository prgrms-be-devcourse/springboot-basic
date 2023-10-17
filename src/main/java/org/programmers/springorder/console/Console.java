package org.programmers.springorder.console;

import org.programmers.springorder.dto.VoucherRequestDto;
import org.programmers.springorder.dto.VoucherResponseDto;
import org.programmers.springorder.model.VoucherType;
import org.programmers.springorder.utils.MenuType;
import org.programmers.springorder.utils.Validation;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);

    private static final String MENU_SELECT_MESSAGE = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.
            """;

    private static final String VOUCHER_SELECT_MESSAGE = """
            Q. 원하는 바우처 타입을 입력해주세요.
            1. Fixed Amount Voucher
            2. Percent Discount Voucher
            """;

    private static final String INPUT_DISCOUNT_VALUE_MESSAGE = """
            Q. 할인 금액을 입력해주세요.
            """;

    // Output
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printPrompt() {
        System.out.print("> ");
    }

    // Input
    public MenuType inputMenu() {
        printMessage(MENU_SELECT_MESSAGE);
        printPrompt();

        String menuName = scanner.nextLine();
        return MenuType.selectMenu(menuName); // TODO: Console Validation
    }

    private VoucherType inputVoucherType() {
        printMessage(VOUCHER_SELECT_MESSAGE);
        printPrompt();

        int voucherNum = Validation.validateInt(scanner.nextLine());
        return VoucherType.selectVoucherType(voucherNum);
    }

    private long inputVoucherValue() {
        printMessage(INPUT_DISCOUNT_VALUE_MESSAGE);
        printPrompt();

        return Validation.validateLong(scanner.nextLine());
    }


    public VoucherRequestDto inputVoucherInfo() {
        VoucherType voucherType = inputVoucherType();
        long discountValue = inputVoucherValue();

        return new VoucherRequestDto(discountValue, voucherType);
    }

    public void showList(List<VoucherResponseDto> allVoucher) {
        if(allVoucher.size() == 0) {
            printMessage("[SYSTEM] 등록된 바우처가 존재하지 않습니다.");
        } else {
            allVoucher.forEach(System.out::println);
        }
    }
}
