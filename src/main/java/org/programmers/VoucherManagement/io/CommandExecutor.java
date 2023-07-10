package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.domain.MemberStatus;
import org.programmers.VoucherManagement.member.dto.CreateMemberRequest;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.member.dto.UpdateMemberRequest;
import org.programmers.VoucherManagement.member.presentation.MemberController;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.UpdateVoucherRequest;
import org.programmers.VoucherManagement.voucher.presentation.VoucherController;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.programmers.VoucherManagement.io.ConsoleMessage.*;

@Component
public class CommandExecutor {
    private final VoucherController voucherController;
    private final MemberController memberController;
    private final Console console;

    public CommandExecutor(VoucherController voucherController, MemberController memberController, Console console) {
        this.voucherController = voucherController;
        this.memberController = memberController;
        this.console = console;
    }

    public void execute(MenuType menuType) {
        switch (menuType) {
            case INSERT_VOUCHER -> {
                console.printConsoleMessage(DISCOUNT_TYPE_MESSAGE);
                CreateVoucherRequest request = makeCreateVoucherRequest();
                voucherController.createVoucher(request);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case UPDATE_VOUCHER -> {
                String voucherId = console.readVoucherId();
                int voucherValue = console.readDiscountValue();
                voucherController.updateVoucher(UUID.fromString(voucherId), new UpdateVoucherRequest(voucherValue));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case DELETE_VOUCHER -> {
                String voucherId = console.readVoucherId();
                voucherController.deleteVoucher(UUID.fromString(voucherId));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case VOUCHER_LIST -> {
                GetVoucherListResponse voucherList = voucherController.getVoucherList();
                console.printVoucherList(voucherList);
            }
            case EXIT -> {
                console.printConsoleMessage(EXIT_MESSAGE);
            }
            case INSERT_MEMBER -> {
                CreateMemberRequest request = makeCreateMemberRequest();
                memberController.createMember(request);
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case UPDATE_MEMBER -> {
                String memberId = console.readMemberId();
                MemberStatus memberStatus = MemberStatus.from(console.readMemberStatus());
                memberController.updateMember(UUID.fromString(memberId), new UpdateMemberRequest(memberStatus));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case DELETE_MEMBER -> {
                String memberId = console.readMemberId();
                memberController.deleteMember(UUID.fromString(memberId));
                console.printConsoleMessage(TASK_SUCCESSFUL_MESSAGE);
            }
            case BLACK_MEMBER_LIST -> {
                GetMemberListResponse blackMemberList = memberController.getAllBlackMembers();
                console.printBlackMemberList(blackMemberList);
            }
            case MEMBER_LIST -> {
                GetMemberListResponse memberList = memberController.getAllMembers();
                console.printAllMemberList(memberList);
            }
        }
    }

    private CreateVoucherRequest makeCreateVoucherRequest() {
        DiscountType discountType = DiscountType.from(console.readDiscountType());
        int discountValue = console.readDiscountValue();

        return new CreateVoucherRequest(discountType, discountValue);
    }

    private CreateMemberRequest makeCreateMemberRequest() {
        String name = console.readMemberName();
        MemberStatus memberStatus = MemberStatus.from(console.readMemberStatus());

        return new CreateMemberRequest(name, memberStatus);
    }

}
