package org.prgrms.kdtspringdemo.domain;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Menu;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.customer.CustomerController;
import org.prgrms.kdtspringdemo.domain.mapping.controller.MappingController;
import org.prgrms.kdtspringdemo.domain.voucher.controller.VoucherDMLTypeController;
import org.prgrms.kdtspringdemo.domain.voucher.controller.VoucherChooseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final Output output;
    private final Input input;

    private final CustomerController customerController;
    private final VoucherChooseController voucherChooseController;
    private final VoucherDMLTypeController voucherDMLTypeController;
    private final MappingController mappingController;

    public MainController(Output output, Input input, CustomerController customerController, VoucherChooseController voucherChooseController, VoucherDMLTypeController voucherDMLTypeController, MappingController mappingController) {
        this.output = output;
        this.input = input;
        this.customerController = customerController;
        this.voucherChooseController = voucherChooseController;
        this.voucherDMLTypeController = voucherDMLTypeController;
        this.mappingController = mappingController;
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
                voucherChooseController.chooseVoucher();
            }
            case LIST -> {
                Menu.LIST.writeStateInfo();
                voucherChooseController.showVoucherList();
            }
            case CUSTOMER -> {
                Menu.CUSTOMER.writeStateInfo();
                customerController.chooseDML();
            }
            case VOUCHER -> {
                Menu.VOUCHER.writeStateInfo();
                voucherDMLTypeController.chooseVoucherManagement();
            }
            case MAPPING -> {
                Menu.VOUCHER.writeStateInfo();
                mappingController.mappingCustomerToVoucher();
            }
            case BLACKLIST -> {
                Menu.BLACKLIST.writeStateInfo();
                customerController.readBlackListCSV();
            }
            case None -> Menu.None.writeStateInfo();
        }

        return workingCondition;
    }
}