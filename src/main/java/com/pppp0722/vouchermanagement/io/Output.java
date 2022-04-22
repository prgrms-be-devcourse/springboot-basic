package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.member.model.Member;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import java.util.List;

public interface Output {

    void printLogo();

    void printMenu();

    void printSuccess();

    void printFailure();

    void printInputError();

    void printEmpty();

    void printVoucherList(List<Voucher> voucherList);

    void printMemberList(List<Member> memberList);
}
