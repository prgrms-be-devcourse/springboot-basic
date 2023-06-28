package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;

import java.util.List;

public interface Output {
    void printType();

    void printDiscountType();

    void printExitMessage();

    void printVoucherList(List<GetVoucherResponse> voucherList);

    void printMemberList(List<GetMemberResponse> memberList);

    void printInputFixedAmountMessage();

    void printInputPercentAmountMessage();
}
