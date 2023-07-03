package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.api.MemberController;
import org.programmers.VoucherManagement.member.dto.GetMemberListResponse;
import org.programmers.VoucherManagement.voucher.api.VoucherController;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.springframework.stereotype.Component;

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

    public void execute(CommandType commandType) {
        switch (commandType) {
            case CREATE -> {
                console.printDiscountType();
                CreateVoucherRequest request = makeCreateVoucherRequest();
                voucherController.createVoucher(request);
            }
            case LIST -> {
                GetVoucherListResponse voucherList = voucherController.getVoucherList();
                console.printVoucherList(voucherList);
            }
            case EXIT -> console.printExitMessage();
            case BLACKLIST -> {
                GetMemberListResponse blackMemberList = memberController.getBlackMemberList();
                console.printMemberList(blackMemberList);
            }
        }
    }

    private CreateVoucherRequest makeCreateVoucherRequest() {
        DiscountType discountType = console.readDiscountType();
        int discountValue = console.readDiscountValue(discountType);

        return new CreateVoucherRequest(discountType, discountValue);
    }
}
