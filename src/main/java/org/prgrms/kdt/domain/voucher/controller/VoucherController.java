package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.service.CustomerService;
import org.prgrms.kdt.util.Console;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.types.VoucherType;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherController(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void processCommand(CommandType commandType){
        logger.info("Process Command Start");
        if(commandType == CommandType.CREATE){
            String voucherInput = Console.inputVoucherType();
            saveVoucher(voucherInput);
        } else if(commandType == CommandType.LIST) {
            findStoredVouchers();
        } else if(commandType == CommandType.EXIT) {
            Console.printExit();
        } else if(commandType == CommandType.BLACKLIST) {
            findBlackCustomers();
        }
        logger.info("Process Command End");
    }

    private void saveVoucher(String voucherInput) {
        logger.info("Create voucher using input: {}", voucherInput);
        VoucherType voucherType = VoucherType.findVoucherType(voucherInput);
        long discount = Console.inputDiscount();
        UUID voucherId = voucherService.save(voucherType, discount);
        logger.info("Created Voucher is {}", voucherId);
    }

    private void findStoredVouchers() {
        List<Voucher> vouchers = voucherService.findAll();
        Console.printAllVouchers(vouchers);
    }

    private void findBlackCustomers() {
        List<Customer> blackListCustomers = customerService.findBlackListCustomers();
        Console.printAllCustomers(blackListCustomers);
    }
}
