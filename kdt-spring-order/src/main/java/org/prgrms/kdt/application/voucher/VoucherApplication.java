package org.prgrms.kdt.application.voucher;

import org.prgrms.kdt.application.voucher.io.Input;
import org.prgrms.kdt.application.voucher.io.Output;
import org.prgrms.kdt.application.voucher.type.CommandType;
import org.prgrms.kdt.application.voucher.type.VoucherType;
import org.prgrms.kdt.domain.voucher.domain.Voucher;
import org.prgrms.kdt.domain.voucher.service.VoucherService;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;

public class VoucherApplication implements Runnable{
    private VoucherService voucherService;
    private Input input;
    private Output output;

    public VoucherApplication(VoucherService voucherService, Input input, Output output) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        output.printProgramName();

        while(true) {
            output.printCommandList();

            Optional<CommandType> optionalCommandType = input.inputCommand();
            if(optionalCommandType.isPresent()) {
                switch (optionalCommandType.get()) {
                    case EXIT -> {
                        exitCommand();
                        return;
                    }
                    case CREATE -> createCommand();
                    case LIST -> listCommand();
                }
            } else {
                output.printInputError();
            }
        }
    }

    private void exitCommand() {
        output.printExit();
    }

    private void createCommand() {
        while(true) {
            output.printVoucherTypeList();

            Optional<VoucherType> optionalVoucherType = input.inputVoucherType();

            if(optionalVoucherType.isPresent()) {
                VoucherType voucherType = optionalVoucherType.get();

                while(true) {
                    OptionalLong optionalLong = input.inputVoucherTypeValue(voucherType.getPrintString());

                    if(optionalLong.isPresent()) {
                        Optional<Voucher> optionalVoucher = voucherType.getVoucher(optionalLong.getAsLong());
                        if(optionalVoucher.isPresent()) {
                            Voucher voucher = optionalVoucher.get();
                            output.printVoucherCreateResult(voucherService.saveVoucher(voucher));
                            break;
                        }
                    }
                    output.printInputError();
                }
                break;
            }
            output.printInputError();
        }
    }

    private void listCommand() {
        Map<UUID, Voucher> voucherMap = voucherService.getAllVoucher();
        if(voucherMap.isEmpty())
            output.printNoneVoucherList();
        else
            output.printVoucherList(voucherMap.values());
    }
}
