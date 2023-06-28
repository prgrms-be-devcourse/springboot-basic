package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.command.domain.CommandType;
import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.member.api.MemberController;
import org.programmers.VoucherManagement.member.dto.GetMemberResponse;
import org.programmers.VoucherManagement.voucher.api.VoucherController;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_EXIST_DISCOUNT_TYPE;

@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private static Console console = new Console();
    private static CommandType commandType;
    private final VoucherController voucherController;
    private final MemberController memberController;

    @Override
    public void run(String... args) throws Exception {
        do {
            console.printType();
            commandType = console.readType();
            execute(commandType);
        } while (!commandType.isExit());
    }

    private void execute(CommandType commandType) {
        if (commandType.isCreate()) {
            console.printDiscountType();
            DiscountType discountType = console.readDiscountType();
            int discountValue = readDiscountValue(discountType);
            voucherController.createVoucher(new CreateVoucherRequest(discountType, discountValue));
        }
        if (commandType.isList()) {
            List<GetVoucherResponse> voucherList = voucherController.getVoucherList();
            console.printVoucherList(voucherList);
        }
        if (commandType.isExit()) {
            console.printExitMessage();
        }
        if(commandType.isBlackList()){
            List<GetMemberResponse> blackMemberList = memberController.getBlackMemberList();
            console.printMemberList(blackMemberList);
        }
    }

    private int readDiscountValue(DiscountType discountType){
        int discountValue;
        switch (discountType) {
            case FIXED -> {
                console.printInputFixedAmountMessage();
                discountValue = console.readFixedDiscountValue();
            }
            case PERCENT -> {
                console.printInputPercentAmountMessage();
                discountValue = console.readPercentDiscountValue();
            }
            default -> throw new IllegalArgumentException(NOT_EXIST_DISCOUNT_TYPE.getMessage());
        }
        return discountValue;
    }
}
