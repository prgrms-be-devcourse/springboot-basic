package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_INCLUDE_1_TO_100;
import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.VOUCHER_AMOUNT_IS_NOT_NUMBER;

@Component
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
    public void printExitMessage() {
        System.out.println(ConsoleMessage.EXIT_MESSAGE);
    }

    @Override
    public void printVoucherList(GetVoucherListResponse getVoucherListResponse) {
        getVoucherListResponse
                .getGetVoucherListRes()
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
    public void printMemberList(GetMemberListResponse memberList) {
        System.out.println(ConsoleMessage.START_VIEW_BLACKLIST_MESSAGE);
        memberList
                .getGetMemberListRes()
                .stream()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("{0} : {1}"
                            , response.getMemberID()
                            , response.getName()));
                });
    }


    @Override
    public MenuType readType() {
        String type = SCANNER.nextLine();
        return MenuType.from(type.toLowerCase());
    }

    @Override
    public DiscountType readDiscountType() {
        String type = SCANNER.nextLine();
        return DiscountType.from(type);
    }

    @Override
    public void printInputAmountMessage() {
        System.out.println("할인 금액(₩ / %)을 입력하세요");
    }


    public int readValue() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(VOUCHER_AMOUNT_IS_NOT_NUMBER.getMessage());
        }
    }

    public int readDiscountValue(DiscountType discountType) {
        printInputAmountMessage();

        int value = readValue();
        if(discountType == DiscountType.PERCENT && !isValidPercentDiscountValue(value)){
            throw new VoucherException(NOT_INCLUDE_1_TO_100);
        }

        return value;
    }

    private boolean isValidPercentDiscountValue(int value) {
        if (value >= 0 && value <= 100) {
            return true;
        }
        return false;
    }
}
