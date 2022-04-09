package org.prgrms.kdt.domain.command.controller;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.util.Console;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommandController {
    private final VoucherService voucherService;

    public CommandController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void processCommand(String commandInput){
        CommandType command = CommandType.findCommand(commandInput);
        if(command == CommandType.CREATE){
            String voucherInput = Console.inputVoucherType();
            createVoucher(voucherInput);
        } else if(command == CommandType.LIST) {
            findStoredVouchers();
        } else if(command == CommandType.EXIT) {
            Console.printExit();
        }
    }

    private void createVoucher(String voucherInput) {
        VoucherType voucherType = VoucherType.findVoucherType(voucherInput);
        long discount = Console.inputDiscount();
        voucherService.save(voucherType, discount);
    }

    private void findStoredVouchers() {
        List<Voucher> vouchers = voucherService.findAll();
        Console.printAllVouchers(vouchers);
    }
}
