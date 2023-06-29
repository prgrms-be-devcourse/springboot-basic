package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.member.api.MemberController;
import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.voucher.api.VoucherController;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherReq;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;
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
            case CREATE:
                console.printDiscountType();
                CreateVoucherReq request = makeCreateVoucherRequest();
                voucherController.createVoucher(request);
                break;
            case LIST:
                GetVoucherListRes voucherList = voucherController.getVoucherList();
                console.printVoucherList(voucherList);
                break;
            case EXIT:
                console.printExitMessage();
                break;
            case BLACKLIST:
                GetMemberListRes blackMemberList = memberController.getBlackMemberList();
                console.printMemberList(blackMemberList);
                break;
        }
    }

    private CreateVoucherReq makeCreateVoucherRequest() {
        DiscountType discountType = console.readDiscountType();
        int discountValue = console.readDiscountValue(discountType);

        return new CreateVoucherReq(discountType, discountValue);
    }
}
