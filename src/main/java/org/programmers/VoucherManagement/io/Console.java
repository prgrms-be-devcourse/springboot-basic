package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;

import static org.programmers.VoucherManagement.io.ConsoleMessage.*;
import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.AMOUNT_IS_NOT_NUMBER;

@Component
public class Console implements Input, Output {
    private static final Scanner SCANNER = new Scanner(System.in);

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
    public void printConsoleMessage(ConsoleMessage message) {
        System.out.println(message);
    }

    @Override
    public void printAllMemberList(GetMemberListResponse memberList) {
        printConsoleMessage(ConsoleMessage.START_VIEW_ALL_MEMBER_MESSAGE);
        printMemberList(memberList);
    }

    @Override
    public void printBlackMemberList(GetMemberListResponse memberList) {
        printConsoleMessage(START_VIEW_BLACKLIST_MESSAGE);
        printMemberList(memberList);
    }


    private void printMemberList(GetMemberListResponse memberList) {
        memberList
                .getGetMemberListRes()
                .stream()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("{0} : {1} -> {2}"
                            , response.getMemberID()
                            , response.getName()
                            , response.getMemberStatus().toString()));
                });
    }


    @Override
    public int readType() {
        return readValue();
    }

    @Override
    public String readDiscountType() {
        return SCANNER.nextLine();
    }

    @Override
    public String readMemberName() {
        printConsoleMessage(MEMBER_NAME_MESSAGE);
        return SCANNER.nextLine();
    }

    @Override
    public String readVoucherId() {
        printConsoleMessage(VOUCHER_ID_MESSAGE);
        return SCANNER.nextLine();
    }

    @Override
    public String readMemberStatus() {
        printConsoleMessage(MEMBER_STATUS_MESSAGE);
        return SCANNER.nextLine();
    }

    @Override
    public int readDiscountValue() {
        printConsoleMessage(DISCOUNT_VALUE_MESSAGE);
        return readValue();
    }

    @Override
    public String readMemberId() {
        printConsoleMessage(MEMBER_ID_MESSAGE);
        return SCANNER.nextLine();
    }

    private int readValue() {
        try {
            return Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(AMOUNT_IS_NOT_NUMBER.getMessage());
        }
    }
}
