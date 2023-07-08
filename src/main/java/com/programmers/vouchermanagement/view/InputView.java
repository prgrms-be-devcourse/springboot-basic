package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.voucher.domain.DiscountType;
import com.programmers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;

import java.util.Scanner;

public class InputView {

    private static final String INPUT_COMMAND = "명령어 번호를 입력해주세요.";
    private static final String INPUT_DISCOUNT_TYPE = "할인 유형 번호를 입력해주세요.";
    private static final String INPUT_DISCOUNT_AMOUNT = "할인 양을 입력해주세요.";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Command inputCommand() {
        System.out.println(INPUT_COMMAND);
        return Command.from(SCANNER.nextLine());
    }

    public static VoucherCreationRequest inputVoucherInfo() {
        DiscountType type = inputDiscountType();
        int amount = inputDiscountAmount();
        return new VoucherCreationRequest(type, amount);
    }

    public static DiscountType inputDiscountType() {
        System.out.println(INPUT_DISCOUNT_TYPE);
        return DiscountType.from(SCANNER.nextLine());
    }

    public static int inputDiscountAmount() {
        System.out.println(INPUT_DISCOUNT_AMOUNT);
        return inputAmount();
    }

    private static int inputAmount() {
        int inputValue = 0;
        try {
            inputValue = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력해주세요");
        }
        return inputValue;
    }
}
