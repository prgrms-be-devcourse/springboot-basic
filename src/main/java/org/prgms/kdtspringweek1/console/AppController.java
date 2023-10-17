package org.prgms.kdtspringweek1.console;

import org.prgms.kdtspringweek1.customer.CustomerService;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AppController {
    private final Scanner scanner = new Scanner(System.in);
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final static Logger logger = LoggerFactory.getLogger(AppController.class);

    public AppController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void startVoucherProgram() {
        selectFunction();
    }

    private void selectFunction() {
        System.out.println("""
                --------------------------------------------------
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type blackList to list all black customers"""
        );
        String functionName = scanner.nextLine();
        try {
            switch (FunctionType.getValueByName(functionName)) {
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
        System.out.println("Voucher Program을 종료합니다.");
        System.exit(0);
    }

    private void createVoucher() {
        System.out.println("""
                --------------------------------------------------
                생성할 voucher 종류를 선택하여 정수로 입력해주세요.
                1. FixedAmountVoucher
                2. PercentDiscountVoucher"""
        );
        long voucherTypeNum, amount = 0, percent = 0;
        voucherTypeNum = scanner.nextLong();
        scanner.nextLine();
        try {
            switch (VoucherType.getValueByNum(voucherTypeNum)) {
                case FIXED_AMOUNT -> {
                    System.out.println("고정된 할인 금액을 입력해주세요.");
                    amount = scanner.nextLong();
                    voucherService.registerVoucher(FixedAmountVoucher.createWithAmount(amount));
                }
                case PERCENT_DISCOUNT -> {
                    System.out.println("할인율을 입력해주세요.");
                    percent = scanner.nextLong();
                    voucherService.registerVoucher(PercentDiscountVoucher.createWithPercent(percent));
                }
            }
            System.out.println("[System] 바우처 생성이 성공적으로 이루어졌습니다.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            createVoucher();
        }
        selectFunction();
    }

    private void getAllVouchers() {
        voucherService.searchAllVouchers()
                .stream()
                .forEach(voucher -> {
                    System.out.println("--------------------------------------------------");
                    voucher.printVoucherInfo();
                });
        System.out.println("""
                --------------------------------------------------
                [System] 바우처 검색이 완료되었습니다."""
        );
        selectFunction();
    }

    private void getAllBlackCustomers() {
        customerService.searchAllBlackCustomers()
                .stream()
                .forEach(customer -> {
                    System.out.println("--------------------------------------------------");
                    customer.printCustomerInfo();
                });
        System.out.println("""
                --------------------------------------------------
                [System] 블랙 리스트 검색이 완료되었습니다."""
        );
        selectFunction();
    }
}
