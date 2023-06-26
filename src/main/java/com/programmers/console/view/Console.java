package com.programmers.console.view;

import com.programmers.console.util.ConsoleMessage;
import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.PercentDiscount;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements InputView, OutputView {

    private static final String ARROW = "> ";
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String userInput() {
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public String inputMenu() {
        print(ConsoleMessage.COMMAND_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public String inputVoucherType() {
        print(ConsoleMessage.CREATE_VOUCHER_TYPE_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public String inputDiscountValue() {
        println(ConsoleMessage.VOUCHER_DISCOUNT_VALUE_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public void printVouchers(List<VoucherResponseDto> vouchers) {
        if (vouchers.isEmpty()) {
            println(ConsoleMessage.LIST_IS_EMPTY.getMessage());
            println("");
            return;
        }
        for (VoucherResponseDto voucher : vouchers) {
            printVoucher(voucher);
        }
        println("");
    }

    @Override
    public void printVoucher(VoucherResponseDto responseDto) {
        println(MessageFormat.format("""
                        Voucher Type   => {0}
                        Voucher ID     => {1}
                        Discount Value => {2}
                        Created Date   => {3}
                        """,
                responseDto.getDiscount().getVoucherType(), responseDto.getVoucherID(),
                discountValueFormat(responseDto.getDiscount()), responseDto.getCreatedDate()));
    }

    public String discountValueFormat(Discount discount) {
        if (discount instanceof PercentDiscount) {
            return discount.getValue() + "%";
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(discount.getValue()) + "₩";
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

}