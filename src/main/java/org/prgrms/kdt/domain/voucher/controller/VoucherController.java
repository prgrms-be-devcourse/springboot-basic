package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.util.Console;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void processCommand(CommandType commandType){
        if(commandType == CommandType.CREATE){
            String voucherInput = Console.inputVoucherType();
            createVoucher(voucherInput);
        } else if(commandType == CommandType.LIST) {
            findStoredVouchers();
        } else if(commandType == CommandType.EXIT) {
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
