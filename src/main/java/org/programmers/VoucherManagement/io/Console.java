package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.response.MemberGetResponses;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponses;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;

import static org.programmers.VoucherManagement.global.response.ErrorCode.AMOUNT_IS_NOT_NUMBER;
import static org.programmers.VoucherManagement.io.ConsoleMessage.*;

@Component
public class Console implements Input, Output {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void printVoucherList(VoucherGetResponses voucherGetResponses) {
        voucherGetResponses
                .getGetVoucherListRes()
                .stream()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("{0} : {1} 타입의 {2}{3} 할인 voucher"
                            , response.voucherId()
                            , response.discountType().getType()
                            , response.discountValue()
                            , response.discountType().getSymbol()));
                });
    }

    @Override
    public void printConsoleMessage(ConsoleMessage message) {
        System.out.println(message);
    }

    @Override
    public void printAllMemberList(MemberGetResponses memberList) {
        printConsoleMessage(ConsoleMessage.START_VIEW_ALL_MEMBER_MESSAGE);
        printMemberList(memberList);
    }

    @Override
    public void printBlackMemberList(MemberGetResponses memberList) {
        printConsoleMessage(START_VIEW_BLACKLIST_MESSAGE);
        printMemberList(memberList);
    }

    @Override
    public void printWalletList(WalletGetResponses walletListResponse) {
        walletListResponse
                .getGetWalletListRes()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("—————" + System.lineSeparator() +
                                    "Wallet - {0}" + System.lineSeparator() +
                                    "Voucher - {1} 타입의 {2}{3} 할인 voucher" + System.lineSeparator() +
                                    "Member - {4} {5}" + System.lineSeparator() +
                                    "—————" + System.lineSeparator()
                            , response.walletId()
                            , response.discountType()
                            , response.discountValue()
                            , response.discountType().getType()
                            , response.memberId()
                            , response.memberName()));
                });
    }


    private void printMemberList(MemberGetResponses memberList) {
        memberList
                .getGetMemberListRes()
                .forEach(response -> {
                    System.out.println(MessageFormat.format("{0} : {1} -> {2}"
                            , response.memberId()
                            , response.name()
                            , response.memberStatus().toString()));
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
    public String readWalletId() {
        printConsoleMessage(WALLET_ID_MESSAGE);
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
