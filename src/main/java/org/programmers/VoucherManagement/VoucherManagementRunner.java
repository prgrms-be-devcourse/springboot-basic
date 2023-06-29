package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.io.CommandType;
import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.member.api.MemberController;
import org.programmers.VoucherManagement.member.dto.GetMemberListRes;
import org.programmers.VoucherManagement.voucher.api.VoucherController;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherReq;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private final Console console;
    private final VoucherController voucherController;
    private final MemberController memberController;

    @Override
    public void run(String... args) throws Exception {
        CommandType commandType;
        boolean isEnd = false;

        while (!isEnd) {
            console.printType();
            commandType = console.readType();

            if (commandType.isExit()){
                isEnd = true;
            }

            execute(commandType);
        }
    }

    private void execute(CommandType commandType) {
        switch (commandType) {
            case CREATE -> {
                console.printDiscountType();
                CreateVoucherReq request = makeCreateVoucherRequest(); // 유효성까지 다 되어있어서 request setting 되어이썽야함.
                voucherController.createVoucher(request);
            }
            case LIST -> {
               GetVoucherListRes voucherList = voucherController.getVoucherList();
                console.printVoucherList(voucherList);
            }
            case EXIT -> {
                console.printExitMessage();
            }
            case BLACKLIST -> {
                GetMemberListRes blackMemberList = memberController.getBlackMemberList();
                console.printMemberList(blackMemberList);
            }
        }
    }

    private CreateVoucherReq makeCreateVoucherRequest() {
        DiscountType discountType = console.readDiscountType();
        int discountValue = console.readDiscountValue(discountType);

        return new CreateVoucherReq(discountType, discountValue);
    }

}
