package org.prgrms.kdt.kdtspringorder.voucher.application;

import org.prgrms.kdt.kdtspringorder.common.enums.Command;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.io.Input;
import org.prgrms.kdt.kdtspringorder.common.io.Output;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;

import java.text.MessageFormat;
import java.util.Scanner;

/**
 * VoucherCommandLine의 메인 로직을 담당합니다.
 */
public class VoucherCommandLine {

    private final String REQUEST_INP_COMMAND_MSG = "명령어를 입력해 주세요. : ";
    private final String REQUEST_SELECT_VOUCHER_TYPE_MSG = "Voucher 유형을 골라주세요.(1) FixedAmountVoucher (2) PercentDiscountVoucher";
    private final String REQUEST_INPUT_DISCOUNT_MSG = "할인 {0}를 입력해주세요.";

    private VoucherService voucherService;
    private Input inputConsole;
    private Output outputConsole;

    public VoucherCommandLine(VoucherService voucherService, Input inputConsole, Output outputConsole) {
        this.voucherService = voucherService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void start() {

        outputConsole.showFirstMsg();

        while ((true)) {

            // 명령어 입력
            String cmdStr = inputConsole.input(REQUEST_INP_COMMAND_MSG).toUpperCase();

            // 명령어에 따른 로직 분기
            if(cmdStr.equals(Command.EXIT.name())) {
                outputConsole.showExitMsg();
                break;
            } else if(cmdStr.equals(Command.CREATE.name())) {
                executeCreateCmd();
            } else if(cmdStr.equals(Command.LIST.name())) {
                executeListCmd();
            } else {
                outputConsole.showIncorrectCmdMsg();
            }

        }

    }

    /**
     * Create 명령어 실행 로직
     */
    public void executeCreateCmd() {

        try {

            // 생성할 Voucher Type을 입력 받는다.
            String voucherNum = inputConsole.input(REQUEST_SELECT_VOUCHER_TYPE_MSG);
            VoucherType selectedVoucherType = VoucherType.findVoucherType(voucherNum);

            // 할인 금액 or 퍼센티지를 입력받는다.
            String discount =  inputConsole.input(MessageFormat.format(REQUEST_INPUT_DISCOUNT_MSG, selectedVoucherType.getUnit()));

            this.voucherService.register(selectedVoucherType, Long.valueOf(discount));

        } catch (IllegalArgumentException e) {

            outputConsole.showInccorectMsg();

        }

    }

    /**
     * List 명령어 실행 로직
     */
    public void executeListCmd() {

        this.voucherService.getVouchers().forEach(v -> {
            System.out.println(v.toString());
        });

    }

}
