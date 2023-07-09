package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;

public interface Output {
    void printConsoleMessage(ConsoleMessage message);

    void printVoucherList(GetVoucherListResponse getVoucherListResponse);

    void printAllMemberList(GetMemberListResponse memberList);

    void printBlackMemberList(GetMemberListResponse memberList);

}
