package com.programmers.console.view;

import com.programmers.console.util.ConsoleMessage;
import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.FixedDiscount;
import com.programmers.voucher.domain.PercentDiscount;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements InputView, OutputView {

    private static final String WRONG_DISCOUNT_TYPE_MESSAGE = "[ERROR] 올바르지 않은 Voucher Type 입니다.";
    private static final String ARROW = "> ";
    private static final String EMPTY_SPACE = "";
    private static final String PERCENT = "%";
    private static final String WON = "₩";

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
        for (VoucherResponseDto voucher : vouchers) {
            printVoucher(voucher);
        }
        println(EMPTY_SPACE);
    }

    @Override
    public void printVoucher(VoucherResponseDto responseDto) {
        println(MessageFormat.format(ConsoleMessage.PRINT_VOUCHER_MESSAGE_FORM.getMessage(),
                responseDto.discount().getVoucherType(), responseDto.voucherId(),
                discountValueFormat(responseDto.discount()), responseDto.createdDate()));
    }

    @Override
    public <T> void println(T message) {
        System.out.println(message);
    }

    @Override
    public <T> void print(T message) {
        System.out.print(message);
    }

    private String discountValueFormat(Discount discount) {
        if (discount instanceof PercentDiscount percentDiscount) {
            return percentDiscount.getPercent() + PERCENT;
        }
        if (discount instanceof FixedDiscount fixedDiscount) {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            return formatter.format(fixedDiscount.getAmount()) + WON;
        }
        throw new IllegalArgumentException(WRONG_DISCOUNT_TYPE_MESSAGE);
    }
}
