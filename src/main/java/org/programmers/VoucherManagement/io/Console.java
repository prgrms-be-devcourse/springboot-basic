package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.CommandType;
import org.programmers.VoucherManagement.DiscountType;
import org.programmers.VoucherManagement.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import static org.programmers.VoucherManagement.exception.ExceptionMessage.NOT_INCLUDE_1_TO_100;
import static org.programmers.VoucherManagement.exception.ExceptionMessage.VOUCHER_AMOUNT_IS_NOT_NUMBER;

public class Console implements Input, Output {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

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
    public void printVoucherList(List<GetVoucherResponse> voucherList) {
        voucherList
                .stream()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("{0} : {1} 타입의 {2}{3} 할인 voucher"
                            , response.getVoucherId()
                            , response.getDiscountType().getType()
                            , response.getDiscountValue()
                            , response.getDiscountType().getSymbol()));
                });
    }

    @Override
    public CommandType readType() {
        String type = SCANNER.nextLine();
        return CommandType.from(type.toLowerCase());
    }

    @Override
    public DiscountType readDiscountType() {
        String type = SCANNER.nextLine();
        return DiscountType.from(type);
    }

    @Override
    public int readFixedDiscountValue() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            logger.info(VOUCHER_AMOUNT_IS_NOT_NUMBER.getMessage());
            throw new NumberFormatException(VOUCHER_AMOUNT_IS_NOT_NUMBER.getMessage());
        }
    }

    @Override
    public int readPercentDiscountValue() {
        try {
            int percent = Integer.parseInt(SCANNER.nextLine());
            if (percent < 0 || percent > 100) {
                logger.info(NOT_INCLUDE_1_TO_100.getMessage());
                throw new VoucherException(NOT_INCLUDE_1_TO_100);
            }
            return percent;
        } catch (NumberFormatException e) {
            logger.info(VOUCHER_AMOUNT_IS_NOT_NUMBER.getMessage());
            throw new NumberFormatException(VOUCHER_AMOUNT_IS_NOT_NUMBER.getMessage());
        }
    }

}
