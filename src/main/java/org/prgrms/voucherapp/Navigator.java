package org.prgrms.voucherapp;

import lombok.AllArgsConstructor;
import org.prgrms.voucherapp.engine.voucher.service.VoucherService;
import org.prgrms.voucherapp.global.Command;
import org.prgrms.voucherapp.global.VoucherType;
import org.prgrms.voucherapp.io.Input;
import org.prgrms.voucherapp.io.Output;
import org.slf4j.Logger;

import java.util.UUID;

@AllArgsConstructor
public class Navigator implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final Logger logger;

    //    TODO : 지금은 모든 exception에 대해서 프로그램 초기부터 시작함. create에서 예외 발생시, create부터 다시 시작할 수 있도록 변경해보자.
    //    TODO : 테스트 코드 작성
    //    TODO : 고객리스트 심화 과제
    //    TODO : properties yaml 외부 설정 주입
    @Override
    public void run() {
        while (true) {
            try {
                output.introMessage();
                Command userCommand = input.commandInput("Type a command : ");
                switch (userCommand) {
                    case EXIT -> {
                        logger.info("User chose exit command.");
                        output.exitMessage();
                    }
                    case CREATE -> {
                        logger.info("User chose create command.");
                        output.informVoucherTypeFormat();
                        VoucherType voucherType = input.voucherTypeInput("Type a voucher type's number : ");
                        long discountAmount = input.discountAmountInput(voucherType, "Type discount amount(0 < x <= %d) : ".formatted(voucherType.getMaxDiscountAmount()));
                        voucherService.createVoucher(voucherType, UUID.randomUUID(), discountAmount);
                        continue;
                    }
                    case LIST -> {
                        logger.info("User chose list command.");
                        output.infoMessage(voucherService.getVoucherListByStr());
                        continue;
                    }
                }
                break;
            } catch (Exception e) {
                logger.error("Exception has occurred for the reason : %s".formatted(e.toString()));
                output.errorMessage(e.toString());
            }
        }

    }
}
