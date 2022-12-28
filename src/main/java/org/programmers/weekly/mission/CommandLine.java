package org.programmers.weekly.mission;

import org.programmers.weekly.mission.domain.customer.service.CustomerService;
import org.programmers.weekly.mission.util.io.Input;
import org.programmers.weekly.mission.util.io.Output;
import org.programmers.weekly.mission.util.type.Message;
import org.programmers.weekly.mission.util.type.OptionType;
import org.programmers.weekly.mission.util.type.VoucherType;
import org.programmers.weekly.mission.domain.voucher.service.VoucherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements Runnable {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Input input;
    private final Output output;
    private boolean isRunnable = true;

    @Autowired
    public CommandLine(VoucherService voucherService, CustomerService customerService, Input input, Output output) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        // isRunnable 필드 -> 변수
        while (isRunnable) {
            switch (selectOptionType()) {
                case CREATE -> createVoucher();
                case LIST -> getListVoucher();
                case EXIT -> exitVoucherProgram();
                case BLACK_LIST -> getBlackList();
            }
        }
    }

    private OptionType selectOptionType() {
        output.printMessage(Message.SELECT_OPTION_MESSAGE.getMessage());
        return OptionType.checkType(input.getInput());
    }

    private VoucherType selectVoucherType() {
        output.printMessage(Message.SELECT_VOUCHER_MESSAGE.getMessage());
        return VoucherType.checkVoucherType(input.getInput());
    }

    private Long selectVoucherDiscount() {
        output.printMessage(Message.SELECT_VOUCHER_INFO.getMessage());
        return input.getVoucherDiscount(input.getInput());
    }

    private void createVoucher() {
        VoucherType voucherType = selectVoucherType();
        Long discount = selectVoucherDiscount();
        voucherService.createVoucher(voucherType, discount);
    }

    private void getListVoucher() {
        output.printMessage(Message.VOUCHER_LIST.getMessage());
        voucherService.getVoucherList().forEach(output::printObject);
    }
    private void exitVoucherProgram() {
        isRunnable = false;
    }

    private void getBlackList() {
        output.printMessage(Message.BLACK_LIST.getMessage());
        customerService.getBlackList().forEach(output::printObject);
    }
}