package com.example.demo.view.voucher;

import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import com.example.demo.view.validate.NumberValidator;
import java.util.Scanner;

public class InputView {

    public static Scanner sc = new Scanner(System.in);

    public CommandType readCommandOption() {
        String input = sc.nextLine();
        NumberValidator.validateCommandNumber(input);
        return CommandType.from(Integer.parseInt(input));
    }

    public VoucherType readVoucherOption() {
        return VoucherType.from(sc.nextLine());
    }

    public int readVoucherAmount(VoucherType voucherType) {
        String input = sc.nextLine();
        NumberValidator.validateAmount(voucherType, input);

        return Integer.parseInt(input);
    }
}
