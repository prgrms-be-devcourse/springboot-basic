package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.io.Console;
import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

public class CommandLine implements Runnable {

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;
    private final String FIXED = "fixed";
    private final String PERCENT = "percent";

    public CommandLine(Console console, ApplicationContext applicationContext) {
        this.input = console;
        this.output = console;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        //프로그램 시작
        while (true) {
            output.start();
            selectMenu(voucherService);
        }
    }

    private void selectMenu(VoucherService voucherService) {
        output.inputTypeMessage();
        String userCommand = input.input();//어떤 작업을 진행할건지 입력받기
        switch (userCommand) {
            case "exit":
                //프로그램 종료
                output.exit();
                break;
            case "list":
                //바우처 조회하기
                voucherService.printVouchers();
                break;
            case "create":
                //바우처 생성하기
                output.voucherTypeMessage();
                String voucherType = input.input();
                long amount = 0L;
                if (FIXED.equals(voucherType)) {
                    output.inputAmountMessage();
                    amount = Long.parseLong(input.input());
                    voucherService.addVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
                } else if (PERCENT.equals(voucherType)) {
                    output.inputAmountMessage();
                    amount = Long.parseLong(input.input());
                    voucherService.addVoucher(new PercentDiscountVoucher(UUID.randomUUID(), amount));
                } else {
                    output.inputError();
                }
                break;
            default:
                output.inputError();
        }
    }
}
