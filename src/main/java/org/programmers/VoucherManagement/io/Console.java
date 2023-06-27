package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.CommandType;
import org.programmers.VoucherManagement.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void printType() {
        System.out.println(ConsoleMessage.START_TYPE_MESSAGE);
    }

    @Override
    public void printDiscountType() {
        System.out.println(ConsoleMessage.DISCOUNT_TYPE_MESSAGE);
    }

    @Override
    public void printDiscountValue() {
        System.out.println(ConsoleMessage.DISCOUNT_VALUE_MESSAGE);
    }

    @Override
    public void printExitMessage() {
        System.out.println(ConsoleMessage.EXIT_MESSAGE);
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        voucherList
                .stream()
                .forEach(voucher -> {
                    System.out.println(MessageFormat.format("{0} : {1} 타입의 {2}{3} 할인 voucher"
                            , voucher.getVoucherId()
                            , voucher.getDiscountType().getType()
                            , voucher.getDiscountValue()
                            , voucher.getDiscountType().getSymbol()));
                });
    }

    @Override
    public CommandType readType() {
        String type = SCANNER.nextLine();
        return CommandType.from(type);
    }

    @Override
    public DiscountType readDiscountType() {
        String type = SCANNER.nextLine();
        return DiscountType.from(type);
    }

    @Override
    public int readDiscountValue() {
        return Integer.parseInt(SCANNER.nextLine());
    }

}
