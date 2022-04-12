package org.prgrms.voucherapp;

import lombok.AllArgsConstructor;
import org.prgrms.voucherapp.engine.VoucherService;
import org.prgrms.voucherapp.global.Command;
import org.prgrms.voucherapp.global.VoucherType;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;

import java.util.UUID;

@AllArgsConstructor
public class Navigator implements Runnable {

    private Input input;
    private Output output;
    private VoucherService voucherService;

    @Override
//    TODO : DiscountAmount validation
    public void run() {
        while (true) {
            try {
                output.introProgram();
                Command userCommand = input.commandInput("Type a command : ");
                switch (userCommand) {
                    case EXIT -> output.exitMessage();
                    case CREATE -> {
                        output.informVoucherTypeFormat();
                        VoucherType voucherType = input.voucherTypeInput("Type a voucher type's number : ");
                        long discountAmount = input.discountAmountInput("Type discount amount : ");
                        //VoucherType의 createVoucher를 voucherService에서만 접근 가능하도록 할 수 있나.
                        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
                        continue;
                    }
                    case LIST -> {
                        System.out.println(voucherService.getVoucherListByStr());
                        continue;
                    }
                }
                break;
            } catch (Exception e) {
                output.errorMessage(e.toString());
            }
        }

    }
}
