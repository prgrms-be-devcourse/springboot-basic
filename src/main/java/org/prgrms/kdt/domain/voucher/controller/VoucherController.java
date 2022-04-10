package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.util.Console;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void processCommand(CommandType commandType){
        logger.info("Process Command Start");
        if(commandType == CommandType.CREATE){
            String voucherInput = Console.inputVoucherType();
            createVoucher(voucherInput);
        } else if(commandType == CommandType.LIST) {
            findStoredVouchers();
        } else if(commandType == CommandType.EXIT) {
            Console.printExit();
        }
        logger.info("Process Command End");
    }

    private void createVoucher(String voucherInput) {
        logger.info("Create voucher using input: {}", voucherInput);
        VoucherType voucherType = VoucherType.findVoucherType(voucherInput);
        long discount = Console.inputDiscount();
        voucherService.save(voucherType, discount);
    }

    private void findStoredVouchers() {
        List<Voucher> vouchers = voucherService.findAll();
        Console.printAllVouchers(vouchers);
    }
}
