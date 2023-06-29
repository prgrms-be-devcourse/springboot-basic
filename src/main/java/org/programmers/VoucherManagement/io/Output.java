package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.member.dto.GetMemberRes;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;

import java.util.List;

public interface Output {
    void printType();

    void printDiscountType();

    void printExitMessage();

    void printVoucherList(GetVoucherListRes getVoucherListRes);

    void printMemberList(GetMemberListRes memberList);

    void printInputAmountMessage();
}
