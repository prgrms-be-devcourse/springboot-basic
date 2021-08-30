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
    private final String INPUT_TYPE_MESSAGE = "Select 'create' or 'list' or 'exit': ";
    private final String VOUCHER_TYPE_MESSAGE = "Select 'fixed' or 'percent': ";
    private final String INPUT_AMOUNT_MESSAGE = "Input Amount: ";
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
            selectMenu(voucherService); //메뉴 선택
        }
    }

    private void selectMenu(VoucherService voucherService) {
        String userCommand = input.input(INPUT_TYPE_MESSAGE);//어떤 작업을 진행할건지 입력받기
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
                String voucherType = input.input(VOUCHER_TYPE_MESSAGE);
                long amount = 0L;
                if (FIXED.equals(voucherType)) {
                    amount = Long.parseLong(input.input(INPUT_AMOUNT_MESSAGE));
                    voucherService.addVoucher(new FixedAmountVoucher(UUID.randomUUID(), amount));
                } else if (PERCENT.equals(voucherType)) {
                    amount = Long.parseLong(input.input(INPUT_AMOUNT_MESSAGE));
                    voucherService.addVoucher(new PercentDiscountVoucher(UUID.randomUUID(), amount));
                } else {
                    output.inputError();
                }
//                Voucher voucher = voucherService.selectVoucher(voucherType);
//                voucherService.addVoucher(voucher);
//                throw new IllegalArgumentException("Write correct type.");
                break;
            default:
                output.inputError();
        }
    }
}
