package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.console.Input;
import org.prgrms.kdtspringdemo.console.Menu;
import org.prgrms.kdtspringdemo.console.Output;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.prgrms.kdtspringdemo.voucher.storage.VoucherMemoryStorage;
import org.prgrms.kdtspringdemo.voucher.storage.VoucherStorage;
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
        boolean workingCondition = true;

        while (workingCondition) {

            System.out.println(output.initMessage());
            Menu menu = input.inputMenu();

            switch (menu) {
                case CREATE -> {
                    System.out.println(output.createListMessage());
                    VoucherType voucherType = input.inputVoucherType();
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
                workingCondition = CreateVoucherAndInsertIntoStorage(amount, workingCondition, VoucherType.PERCENT);
            }
            case ERROR -> workingCondition = false;
        }

        return workingCondition;
    }

    private boolean CreateVoucherAndInsertIntoStorage(int input, boolean workingCondition, VoucherType fixed) {
        int fixedAmount = input;

        if (fixedAmount == -1) {
            workingCondition = false;
        } else {
            voucherService.createVoucher(fixed, fixedAmount);
        }

        return workingCondition;
    }
}
