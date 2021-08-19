package com.prgrms.devkdtorder.cla;

import com.prgrms.devkdtorder.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommandLineApplication implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        while (true) {
            output.printAppStart();
            String command = input.getCommand().toUpperCase();
            if (!validateCommand(command)){
                output.printCommandError();
                continue;
            }

            CommandType commandType = CommandType.valueOf(command);
            if (commandType.equals(CommandType.EXIT)){
                break;
            }


            boolean result = executeCommand(commandType);


        }



    }

    private boolean executeCommand(CommandType commandType) {
        boolean result = false;
        switch (commandType) {
            case CREATE:
                result = createVoucher();
                break;
            case LIST:
                showVouchers();
                break;
        }
        return result;
    }

    private boolean createVoucher(){
        List<String> list = VoucherType.voucherTypeNames();
        output.print(list);

        String type = input.getVoucherType();
        Optional<VoucherType> voucherType = VoucherType.findByNameOrNo(type);
        if (voucherType.isEmpty()){
            output.printVoucherInputError();
            return false;
        }
        long value = input.getVoucherValue();

        Voucher voucher = VoucherFactory.create(voucherType.get(), value);
        voucherService.saveVoucher(voucher);
        return true;
    }

    private void showVouchers() {
        List<Voucher> vouchers = voucherService.getAllVouchers();
        List<String> metas = vouchers.stream()
                .map(Voucher::toString)
                .collect(Collectors.toList());
        output.print(metas);

    }

    private boolean validateCommand(String command) {
        return CommandType.anyMatch(command);
    }
}
