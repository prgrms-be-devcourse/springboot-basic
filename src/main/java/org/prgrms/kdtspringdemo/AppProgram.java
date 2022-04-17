package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.console.Input;
import org.prgrms.kdtspringdemo.console.Menu;
import org.prgrms.kdtspringdemo.console.Output;
import org.prgrms.kdtspringdemo.customer.CustomerService;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppProgram {
    private static final Logger logger = LoggerFactory.getLogger(AppProgram.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public AppProgram(Output output, Input input, VoucherService voucherService, CustomerService customerService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    // App 시작
    public void startApp() {
        // while 문 탈출을 workingCondition 으로 제어
        logger.info("App 시작");
        boolean workingCondition = true;

        while (workingCondition) {
            workingCondition = startProgram(workingCondition);
        }

        input.closeScanner();
    }

    // 메뉴 선택(EXIT, CREAT, LIST)
    private boolean startProgram(boolean workingCondition) {
        logger.info("메뉴 선택(EXIT, CREAT, LIST)");
        System.out.println(output.initMessage());
        Menu menu = input.inputMenu();

        switch (menu) {
            case EXIT -> {
                Menu.EXIT.writeStateInfo();
                logger.info("EXIT 선택 후 프로그램 종료");

                return !workingCondition;
            }
            case CREATE -> {
                Menu.CREATE.writeStateInfo();
                chooseVoucher();
            }
            case LIST -> {
                Menu.LIST.writeStateInfo();
                voucherService.showVoucherList();
            }
            case BLACKLIST -> {
                Menu.BLACKLIST.writeStateInfo();
                customerService.addBlackList();
            }
            case None -> Menu.None.writeStateInfo();
        }

        return workingCondition;
    }

    // Voucher 타입 선택(Fixed, Percent)
    private void chooseVoucher() {
        System.out.println(output.chooseVoucherTypeMessage());
        VoucherType voucherType = input.inputVoucherType();

        switch (voucherType) {
            case FIXED -> {
                VoucherType.FIXED.writeStateInfo();
                System.out.println(output.FixedDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                voucherService.createFixedVoucher(amount);
            }
            case PERCENT -> {
                VoucherType.PERCENT.writeStateInfo();
                System.out.println(output.PercentDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                voucherService.createPercentVoucher(amount);
            }
            case None -> VoucherType.None.writeStateInfo();
        }
    }
}




