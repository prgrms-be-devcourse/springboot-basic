package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;

public interface Output {
    void printType();

    void printDiscountType();

    void printExitMessage();

    void printVoucherList(GetVoucherListResponse getVoucherListResponse);

    void printMemberList(GetMemberListResponse memberList);

    void printInputAmountMessage();
}
