package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.application.dto.MemberCreateRequest;
import org.programmers.VoucherManagement.member.application.dto.MemberGetResponses;
import org.programmers.VoucherManagement.member.application.dto.MemberUpdateRequest;
import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.presentation.MemberController;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherUpdateRequest;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.presentation.VoucherController;
import org.programmers.VoucherManagement.wallet.application.dto.WalletCreateRequest;
import org.programmers.VoucherManagement.wallet.application.dto.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.presentation.WalletController;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.programmers.VoucherManagement.io.ConsoleMessage.*;

@Component
public class CommandExecutor {
    private final VoucherController voucherController;
    private final MemberController memberController;
    private final WalletController walletController;
    private final Console console;

    public CommandExecutor(VoucherController voucherController,
                           MemberController memberController,
                           WalletController walletController,
                           Console console) {
        this.voucherController = voucherController;
        this.memberController = memberController;
        this.walletController = walletController;
        this.console = console;
    }

    public void execute(MenuType menuType) {
        switch (menuType) {
            case INSERT_VOUCHER -> {
                console.printConsoleMessage(DISCOUNT_TYPE_MESSAGE);
                VoucherCreateRequest request = makeCreateVoucherRequest();
                voucherController.createVoucher(request);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case UPDATE_VOUCHER -> {
                String voucherId = console.readVoucherId();
                int voucherValue = console.readDiscountValue();
                voucherController.updateVoucher(UUID.fromString(voucherId), new VoucherUpdateRequest(voucherValue));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case DELETE_VOUCHER -> {
                String voucherId = console.readVoucherId();
                voucherController.deleteVoucher(UUID.fromString(voucherId));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case VOUCHER_LIST -> {
                VoucherGetResponses voucherList = voucherController.getVoucherList();
                console.printVoucherList(voucherList);
            }
            case EXIT -> {
                console.printConsoleMessage(EXIT_MESSAGE);
            }
            case INSERT_MEMBER -> {
                MemberCreateRequest request = makeCreateMemberRequest();
                memberController.createMember(request);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case UPDATE_MEMBER -> {
                String memberId = console.readMemberId();
                MemberStatus memberStatus = MemberStatus.from(console.readMemberStatus());
                memberController.updateMember(memberId, new MemberUpdateRequest(memberStatus));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case DELETE_MEMBER -> {
                String memberId = console.readMemberId();
                memberController.deleteMember(memberId);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case BLACK_MEMBER_LIST -> {
                MemberGetResponses blackMemberList = memberController.getAllBlackMembers();
                console.printBlackMemberList(blackMemberList);
            }
            case MEMBER_LIST -> {
                MemberGetResponses memberList = memberController.getAllMembers();
                console.printAllMemberList(memberList);
            }
            case INSERT_WALLET -> {
                WalletCreateRequest request = makeCreateWalletRequest();
                walletController.createWallet(request);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case LIST_WALLET_BY_MEMBER -> {
                String memberId = console.readMemberId();
                WalletGetResponses walletList = walletController.getWalletsByMemberId(memberId);
                console.printWalletList(walletList);
            }
            case LIST_WALLET_BY_VOUCHER -> {
                String voucherId = console.readVoucherId();
                WalletGetResponses walletList = walletController.getWalletsByVoucherId(UUID.fromString(voucherId));
                console.printWalletList(walletList);
            }
            case DELETE_WALLET -> {
                String walletId = console.readWalletId();
                walletController.deleteWallet(UUID.fromString(walletId));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
        }
    }

    private VoucherCreateRequest makeCreateVoucherRequest() {
        DiscountType discountType = DiscountType.from(console.readDiscountType());
        int discountValue = console.readDiscountValue();

        return new VoucherCreateRequest(discountType.toString(), discountValue);
    }

    private MemberCreateRequest makeCreateMemberRequest() {
        String name = console.readMemberName();
        MemberStatus memberStatus = MemberStatus.from(console.readMemberStatus());

        return new MemberCreateRequest(name, memberStatus);
    }

    private WalletCreateRequest makeCreateWalletRequest() {
        String voucherId = console.readVoucherId();
        String memberId = console.readMemberId();

        return new WalletCreateRequest(voucherId, memberId);
    }
}
