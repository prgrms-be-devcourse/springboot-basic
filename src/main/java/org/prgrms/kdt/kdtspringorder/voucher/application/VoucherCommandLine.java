package org.prgrms.kdt.kdtspringorder.voucher.application;

import org.prgrms.kdt.kdtspringorder.common.enums.Command;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.io.Input;
import org.prgrms.kdt.kdtspringorder.common.io.Output;
import org.prgrms.kdt.kdtspringorder.custommer.domain.Customer;
import org.prgrms.kdt.kdtspringorder.custommer.service.BlackListCustomerService;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

/**
 * VoucherCommandLine의 메인 로직을 담당합니다.
 */
@Component
public class VoucherCommandLine {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandLine.class);

    private final String REQUEST_INP_COMMAND_MSG = "명령어를 입력해 주세요. : ";
    private final String REQUEST_SELECT_VOUCHER_TYPE_MSG = "Voucher 유형을 골라주세요.(1) FixedAmountVoucher (2) PercentDiscountVoucher";
    private final String REQUEST_INPUT_DISCOUNT_MSG = "할인 {0}를 입력해주세요.";

    private VoucherService voucherService;
    private BlackListCustomerService blackListCustomerService;

    private Input inputConsole;
    private Output outputConsole;

    public VoucherCommandLine(VoucherService voucherService, BlackListCustomerService blackListCustomerService, Input inputConsole, Output outputConsole) {
        this.voucherService = voucherService;
        this.blackListCustomerService = blackListCustomerService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    /**
     * Voucher Command Line 어플리케이션을 시작합니다.
     */
    public void start() {

        outputConsole.showFirstMsg();

        List<Customer> blackList = this.blackListCustomerService.getBlackList();
        outputConsole.showObjectList("BLACKLIST", blackList);

        while ((true)) {

            // 명령어 입력
            String cmdStr = inputConsole.input(REQUEST_INP_COMMAND_MSG).toUpperCase();

            logger.info(MessageFormat.format("Command Input : {0}", cmdStr));

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
            String voucherTypeFlag = convertInputVoucherNumToVoucherTypeFlag(voucherNum);

            VoucherType selectedVoucherType = VoucherType.findVoucherType(voucherTypeFlag);

            // 할인 금액 or 퍼센티지를 입력받는다.
            String discount =  inputConsole.input(MessageFormat.format(REQUEST_INPUT_DISCOUNT_MSG, selectedVoucherType.getUnit()));
            UUID savedVoucherId = this.voucherService.saveVoucher(selectedVoucherType, Long.valueOf(discount));

            Voucher savedVoucher = this.voucherService.getVoucher(savedVoucherId);

            logger.info(MessageFormat.format("Success Create Voucher : {0}", savedVoucher.toString()));

        } catch (IllegalArgumentException e) {

            outputConsole.showIncorrectNumMsg();
            logger.info("Fail Create Voucher : Incorrect Comand");

        }

    }

    private String convertInputVoucherNumToVoucherTypeFlag(String voucherNum) {
        String voucherTypeFlag = null;
        switch (voucherNum) {
            case "1":
                voucherTypeFlag = "F";
                break;
            case "2":
                voucherTypeFlag = "P";
        }
        return voucherTypeFlag;
    }

    /**
     * List 명령어 실행 로직
     */
    public void executeListCmd() {

        List<Voucher> vouchers = this.voucherService.getVouchers();
        outputConsole.showObjectList("Voucher List", vouchers);

    }

}
