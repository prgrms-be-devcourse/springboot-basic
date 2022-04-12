package org.prgrms.voucherapp;

import lombok.AllArgsConstructor;
import org.prgrms.voucherapp.engine.VoucherService;
import org.prgrms.voucherapp.exception.WrongAmountException;
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

//    TODO : 지금은 모든 exception에 대해서 프로그램 초기부터 시작함. create에서 예외 발생시, create부터 다시 시작할 수 있도록 변경.
    @Override
    public void run() {
        while (true) {
            try {
                output.introMessage();
                Command userCommand = input.commandInput("Type a command : ");
                switch (userCommand) {
                    case EXIT -> output.exitMessage();
                    case CREATE -> {
                        output.informVoucherTypeFormat();
                        VoucherType voucherType = input.voucherTypeInput("Type a voucher type's number : ");
                        long discountAmount = input.discountAmountInput(voucherType, "Type discount amount(0 < x < %d) : ".formatted(voucherType.getMaxDiscountAmount()));
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
