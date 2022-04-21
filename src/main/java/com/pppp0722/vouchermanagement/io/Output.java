package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.entity.member.Member;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;

import java.util.List;

public interface Output {

    void printLogo();

    void printMenu();

    void printEmpty();

    void printInputError();

    void printError(String error);

    void printEntityTypeInputRequest();

    void printVoucherTypeInputRequest();

    void printNameInputRequest();

    void printAmountInputRequest();

    void printMemberIdInputRequest();

    void printVoucherEmpty();

    void printVoucherList(List<Voucher> voucherList);

    void printMemberListEmpty();

    void printMemberList(List<Member> memberList);
}
