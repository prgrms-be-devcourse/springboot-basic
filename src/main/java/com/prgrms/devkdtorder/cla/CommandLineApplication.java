package com.prgrms.devkdtorder.cla;

import com.prgrms.devkdtorder.*;

import java.util.List;
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
            String command = input.getCommand();
            if (!validateCommand(command.toUpperCase())){
                output.printCommandError();
                continue;
            }

            CommandType ct = CommandType.valueOf(command.toUpperCase());
            if (ct.equals(CommandType.EXIT)){
                break;
            }


            String result = executeCommand(ct);
            if (!result.isEmpty()){
                output.print(result);
                continue;
            }

        }



    }

    private String executeCommand(CommandType cmdType) {
        String result = "";
        switch (cmdType) {
            case CREATE:
                createVoucher();
                break;
            case LIST:
                showVouchers();
                break;
        }
        return result;
    }

    private String createVoucher(){
        List<String> list = VoucherType.voucherTypeNames();
        output.print(list);

        String type = input.getVoucherType();
        VoucherType voucherType = VoucherType.valueOf(type.toUpperCase());
        long value = input.getVoucherValue();

        Voucher voucher = VoucherFactory.create(voucherType, value);
        voucherService.saveVoucher(voucher);
        return "";
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
