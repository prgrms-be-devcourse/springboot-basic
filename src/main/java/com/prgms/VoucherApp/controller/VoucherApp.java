package com.prgms.VoucherApp.controller;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherCreator;
import com.prgms.VoucherApp.domain.VoucherPolicy;
import com.prgms.VoucherApp.domain.VoucherReader;
import com.prgms.VoucherApp.view.Command;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherApp implements Runnable {

    private final VoucherCreator voucherCreator;
    private final VoucherReader voucherReader;
    private final Input input;
    private final Output output;

    public VoucherApp(VoucherCreator voucherCreator, VoucherReader voucherReader, Input input, Output output) {
        this.voucherCreator = voucherCreator;
        this.voucherReader = voucherReader;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            output.printDisplayMenu();
            String inputCommand = input.inputCommand();
            Command command = Command.findByCommand(inputCommand);
            switch (command) {
                case EXIT -> {
                    return;
                }

                case CREATE -> {
                    VoucherPolicy policy = getVoucherPolicyType();
                    long amount = getDiscountAmount(policy);
                    Voucher voucher = voucherCreator.createVoucher(policy, amount);
                    if (voucher == null) {
                        output.printNotCreatedMsg();
                        break;
                    }
                    output.printCreatedMsg(voucher);
                }

                case LIST -> {
                    List<Voucher> vouchers = voucherReader.findAll();
                    output.printVoucherList(vouchers);
                }
            }
        }
    }

    private VoucherPolicy getVoucherPolicyType() {
        output.printDisplayVoucherPolicy();
        String inputVoucherPolicy = input.inputVoucherPolicy();
        return VoucherPolicy.findByPolicy(inputVoucherPolicy);
    }

    private long getDiscountAmount(VoucherPolicy policy) {
        output.printDisplayDiscountCondition(policy);
        return input.inputDiscountAmount(policy);
    }
}
