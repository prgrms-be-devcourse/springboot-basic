package org.prgrms.kdt.kdtspringorder.voucher.application;

import org.prgrms.kdt.kdtspringorder.common.enums.Command;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;

import java.text.MessageFormat;
import java.util.Scanner;

/**
 * VoucherCommandLine의 메인 로직을 담당합니다.
 */
public class VoucherCommandLine {

    private final String COMMAND_ANNOUNCE_MSG = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
    private final String REQUEST_INP_COMMAND_MSG = "명령어를 입력해 주세요. : ";
    private final String REQUEST_SELECT_VOUCHER_TYPE_MSG = "Voucher 유형을 골라주세요.(1) FixedAmountVoucher (2) PercentDiscountVoucher";
    private final String REQUEST_INPUT_DISCOUNT_MSG = "할인 {0}을 입력해주세요.";
    private final String INCORRECT_COMMAND_MSG = "잘못된 명령어 입니다. ( create, list, exit )";
    private final String EXIT_COMMAND_MSG = "앱을 종료합니다.";

    private final Scanner scanner = new Scanner(System.in);

    private VoucherService voucherService;

    public VoucherCommandLine(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void start() {

        while ((true)) {

            // 명령어 입력
            String cmdStr = this.input(REQUEST_INP_COMMAND_MSG).toUpperCase();

            // 명령어에 따른 로직 분기
            if(cmdStr.equals(Command.EXIT.name())) {
                this.showExitMsg();
                break;
            } else if(cmdStr.equals(Command.CREATE.name())) {
                executeCreateCmd();
            } else if(cmdStr.equals(Command.LIST.name())) {
                executeListCmd();
            } else {
                this.showIncorrectCmdMsg();
            }

        }

    }

    /**
     * Create 명령어 실행 로직
     */
    public void executeCreateCmd() {

        // 생성할 Voucher Type을 입력 받는다.
        String voucherNum = this.input(REQUEST_SELECT_VOUCHER_TYPE_MSG);
        VoucherType selectedVoucherType = VoucherType.findVoucher(voucherNum);

        // 할인 금액 or 퍼센티지를 입력받는다.
        String discount =  this.input(MessageFormat.format(REQUEST_INPUT_DISCOUNT_MSG, selectedVoucherType.getUnit()));

        this.voucherService.createVoucher(selectedVoucherType, Long.valueOf(discount));

    }

    /**
     * List 명령어 실행 로직
     */
    public void executeListCmd() {

    }

    /**
     * 콘솔 입력을 받습니다.
     * @param prompt 입력 받을 떄, 출력할 메시지
     * @return 입력한 내용
     */
    public String input(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }


    public void showFirstMsg() {
        System.out.println(COMMAND_ANNOUNCE_MSG);
    }

    public void showIncorrectCmdMsg() {
        System.out.println(INCORRECT_COMMAND_MSG);
    }

    public void showExitMsg() {
        System.out.println(EXIT_COMMAND_MSG);
    }

}
