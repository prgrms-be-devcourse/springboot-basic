package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.dto.response.MemberGetResponses;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.wallet.dto.response.WalletGetResponses;

public interface Output {
    void printConsoleMessage(ConsoleMessage message);

    void printVoucherList(VoucherGetResponses voucherGetResponses);

    void printAllMemberList(MemberGetResponses memberList);

    void printBlackMemberList(MemberGetResponses memberList);

    void printWalletList(WalletGetResponses walletListResponse);
}
