package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;

public interface Output {
    void printType();

    void printDiscountType();

    void printExitMessage();

    void printVoucherList(GetVoucherListRes getVoucherListRes);

    void printMemberList(GetMemberListRes memberList);

    void printInputAmountMessage();
}
