package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleInput;
import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.customer.CustomerService;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.console.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 뷰 영역(console 패키지)에 컨트롤러가 포함되어 있어, 따로 빼주었습니다.
// 기존에, 입출력 관련 기능들이 컨트롤러에 명시적으로 드러나 있어, 콘솔 입출력의 경우 따로 클래스를 두어 구현
@Component
public class AppController {
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    public AppController(ConsoleInput consoleInput, ConsoleOutput consoleOutput, VoucherService voucherService, CustomerService customerService) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void startVoucherProgram() {
        selectFunction();
    }

    private void selectFunction() {
        consoleOutput.printFunctionsToSelect();
        try {
            switch (consoleInput.getFunctionType()) {
                case EXIT -> exitVoucherProgram();
                case CREATE -> createVoucher();
                case LIST -> getAllVouchers();
                case BLACK_LIST -> getAllBlackCustomers();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            selectFunction();
        }
    }

    private void exitVoucherProgram() {
        consoleOutput.printExitMessage();
        System.exit(0);
    }

    private void createVoucher() {
        consoleOutput.printVouchersToSelect();
        try {
            switch (consoleInput.getVoucherType()) {
                case FIXED_AMOUNT -> {
                    consoleOutput.printRequestMessageToDecideFixedAmount();
                    voucherService.registerVoucher(FixedAmountVoucher.createWithAmount(consoleInput.getDiscountValue()));
                }
                case PERCENT_DISCOUNT -> {
                    consoleOutput.printRequestMessageToDecidePercentDiscount();
                    voucherService.registerVoucher(PercentDiscountVoucher.createWithPercent(consoleInput.getDiscountValue()));
                }
            }
            consoleOutput.printSuccessToCreateVoucher();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            createVoucher();
        }
        selectFunction();
    }

    private void getAllVouchers() {
        try {
            voucherService.searchAllVouchers()
                    .stream()
                    .forEach(voucher -> {
                        voucher.printVoucherInfo();
                    });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        consoleOutput.printSuccessToSearchVouchers();
        selectFunction();
    }

    private void getAllBlackCustomers() {
        try {
            customerService.searchAllBlackCustomers()
                    .forEach(FindCustomerResponseDto::printCustomerInfo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
        }
        consoleOutput.printSuccessToSearchBlackCustomers();
        selectFunction();
    }
}
