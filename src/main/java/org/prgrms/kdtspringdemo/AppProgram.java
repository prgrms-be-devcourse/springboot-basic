package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.console.Input;
import org.prgrms.kdtspringdemo.console.Menu;
import org.prgrms.kdtspringdemo.console.Output;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppProgram {
    private final Output output = new Output();
    private final Input input = new Input();

    private final VoucherService voucherService;

    @Autowired
    public AppProgram(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void startApp() {
        // while 문 탈출을 workingCondition 으로 제어
        boolean workingCondition = true;

        while (workingCondition) {
            // 초기 메뉴 선택
            System.out.println(output.initMessage());
            Menu menu = input.inputMenu();

            switch (menu) {
                case CREATE -> {
                    System.out.println(output.createListMessage());
                    VoucherType voucherType = input.inputVoucherType();
                    // CREATE 선택 후 타입 지정(ERROR 의 경우 workingCondition false 반환)
                    workingCondition = createWithVoucherType(workingCondition, voucherType);

                }
                case LIST -> {
                    System.out.println(output.showAllMessage());
                    voucherService.showAllVoucher();
                }
                case EXIT, ERROR -> workingCondition = false;
            }
        }
    }

    private boolean createWithVoucherType(boolean workingCondition, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> {
                int amount = input.inputAmount(voucherType);
                workingCondition = CreateVoucherAndInsertIntoStorage(amount, workingCondition, VoucherType.FIXED);
            }
            case PERCENT -> {
                int amount = input.inputAmount(voucherType);
                // 타입 선택 후 입력받은 할인 금액 처리(ERROR 의 경우 workingCondition false 반환)
                workingCondition = CreateVoucherAndInsertIntoStorage(amount, workingCondition, VoucherType.PERCENT);
            }
            // 에러의 경우 workingCondition false 반환
            case ERROR -> workingCondition = false;
        }

        return workingCondition;
    }

    private boolean CreateVoucherAndInsertIntoStorage(int input, boolean workingCondition, VoucherType fixed) {
        int fixedAmount = input;

        if (fixedAmount == -1) {
            workingCondition = false;
        } else {
            // 선택 타입과 할인 금액을 통해 Voucher 생성 및 메모리 저장
            voucherService.createVoucher(fixed, fixedAmount);
        }

        return workingCondition;
    }
}
