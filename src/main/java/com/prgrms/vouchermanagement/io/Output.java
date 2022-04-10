package com.prgrms.vouchermanagement.io;

import com.prgrms.vouchermanagement.Member;
import com.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;

public interface Output {
    void printMenu();

    void printMessage(String errorMessage);

    void printList(List<Voucher> vouchers);

    void printBlackList(List<Member> blackList);
}
