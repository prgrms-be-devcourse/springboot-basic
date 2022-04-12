package org.prgrms.voucherapp;

import org.prgrms.voucherapp.engine.Voucher;
import org.prgrms.voucherapp.exception.WrongInputException;
import org.prgrms.voucherapp.global.Menu;
import org.prgrms.voucherapp.global.VoucherType;
import org.prgrms.voucherapp.io.Input;

import java.util.Scanner;

public class Console implements Input {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Menu commandInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        return Menu
                .getMenu(scanner.nextLine())
                .orElseThrow(()->(new WrongInputException("존재하지 않는 메뉴입니다.")));
    }

    @Override
    public VoucherType voucherTypeInput(String prompt) throws WrongInputException {
        System.out.println(prompt);
        return VoucherType
                .getType(scanner.nextLine())
                .orElseThrow(()->(new WrongInputException("존재하지 않는 바우처 타입입니다.")));
    }
}
