package com.example.demo.view;

import com.example.demo.util.CommandType;
import com.example.demo.util.VoucherType;
import com.example.demo.view.validate.VoucherAmountValidator;
import java.util.Scanner;

public class InputView {

    public static Scanner sc = new Scanner(System.in);

    public CommandType readCommandOption() {
        return CommandType.find(sc.nextLine());
    }

    public VoucherType readVoucherOption() {
        return VoucherType.from(sc.nextLine());
    }

    public int readVoucherAmount(VoucherType voucherType) {
        String input = sc.nextLine();
        VoucherAmountValidator.validateAmount(voucherType, input);

        return Integer.parseInt(input);
    }
}
