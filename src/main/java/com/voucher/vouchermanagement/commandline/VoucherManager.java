package com.voucher.vouchermanagement.commandline;

import com.voucher.vouchermanagement.domain.customer.dto.CustomerDto;
import com.voucher.vouchermanagement.domain.voucher.dto.VoucherDto;
import com.voucher.vouchermanagement.commandline.command.CommandType;
import com.voucher.vouchermanagement.commandline.io.VoucherManagerInput;
import com.voucher.vouchermanagement.commandline.io.VoucherManagerOutput;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherType;
import com.voucher.vouchermanagement.domain.blacklist.service.BlacklistService;
import com.voucher.vouchermanagement.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherManager {

    private final VoucherService voucherService;
    private final BlacklistService blackListService;
    private final VoucherManagerInput input;
    private final VoucherManagerOutput output;
    private static final Logger logger = LoggerFactory.getLogger(VoucherManager.class);

    public VoucherManager(VoucherService voucherService, BlacklistService blackListService,
                          VoucherManagerInput input, VoucherManagerOutput output) {
        this.voucherService = voucherService;
        this.blackListService = blackListService;
        this.input = input;
        this.output = output;
    }

    public void run() {
        CommandType commandType = CommandType.NONE;

        while (commandType != CommandType.EXIT) {
            try {
                this.output.printMenu();
                commandType = CommandType.getCommandTypeByName(input.input("input command : "));
                switch (commandType) {
                    case CREATE: {
                        createVoucher();
                        break;
                    }
                    case LIST: {
                        printVouchers();
                        break;
                    }
                    case BLACKLIST: {
                        printBlacklist();
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                logAndPrintException(e);
            }
        }
    }

    private void createVoucher() {
        try {
            this.output.println("=== [Create Voucher] ===");
            this.output.printVoucherType();
            int voucherTypeNumberInput = Integer.parseInt(this.input.input("Input voucher type : "));
            VoucherType voucherType = VoucherType.getVoucherTypeByNumber(voucherTypeNumberInput);
            long voucherValue = Long.parseLong(this.input.input("Input voucher value : "));

            this.voucherService.create(voucherType, voucherValue);
            this.output.println("Voucher Creation Completed.");
        } catch (RuntimeException e) {
            logAndPrintException(e);
        }
    }

    private void printVouchers() {
        this.output.println("=== [Voucher List] ===");
        List<VoucherDto> vouchers = this.voucherService.findAll();

        for (VoucherDto voucher : vouchers) {
            this.output.println(voucher.toString());
        }
    }

    private void printBlacklist() {
        this.output.println("===  [Blacklist]  ===");
        List<CustomerDto> blacklist = this.blackListService.findAll();

        for (CustomerDto user : blacklist) {
            this.output.println(user.toString());
        }
    }

    private void logAndPrintException(Exception exception) {
        logger.error(exception.getMessage());
        this.output.println(exception.getMessage());
    }
}
