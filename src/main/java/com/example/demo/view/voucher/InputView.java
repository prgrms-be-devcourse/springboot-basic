package com.example.demo.view.voucher;

import com.example.demo.enums.VoucherCommandType;
import com.example.demo.enums.VoucherDiscountType;
import com.example.demo.view.validate.CommandValidator;
import com.example.demo.view.validate.NumberValidator;
import java.util.Scanner;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InputView {

    public static Scanner sc = new Scanner(System.in);

    public VoucherCommandType readVoucherCommandOption() {
        String input = sc.nextLine();
        CommandValidator.validateCommandNumberOneToFour(input);
        return VoucherCommandType.from(Integer.parseInt(input));
    }

    public VoucherDiscountType readVoucherDiscountOption() {
        return VoucherDiscountType.from(sc.nextLine());
    }

    public int readVoucherAmount(VoucherDiscountType voucherDiscountType) {
        String input = sc.nextLine();
        NumberValidator.validateAmount(voucherDiscountType, input);

        return Integer.parseInt(input);
    }

    public int readVoucherAmountWithoutValidation() {
        String input = sc.nextLine();
        return Integer.parseInt(input);
    }

    public UUID readVoucherId() {
        String input = sc.nextLine();
        try {
            return UUID.fromString(input);
        } catch (IllegalArgumentException e) {
            log.error("View 에러 : 올바르지 않은 포맷의 UUID를 입력 받았습니다. 입력 값 {} 를 UUID 포맷에 맞추어 고쳐주세요.", input);
            throw new IllegalArgumentException(String.format("올바르지 않은 포맷의 UUID를 입력 받았습니다. 입력 값 %s 를 UUID 포맷에 맞추어 고쳐주세요.", input));
        }
    }
}
