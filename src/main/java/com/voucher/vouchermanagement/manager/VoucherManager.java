package com.voucher.vouchermanagement.manager;

import com.voucher.vouchermanagement.manager.command.CommandType;
import com.voucher.vouchermanagement.manager.io.VoucherManagerInput;
import com.voucher.vouchermanagement.manager.io.VoucherManagerOutput;
import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.blacklist.BlacklistService;
import com.voucher.vouchermanagement.service.voucher.VoucherService;
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

            this.voucherService.createVoucher(voucherType, voucherValue);
            this.output.println("Voucher Creation Completed.");
        } catch (IllegalArgumentException e) {
            logAndPrintException(e);
        }
    }

    private void printVouchers() {
        this.output.println("=== [Voucher List] ===");
        List<Voucher> vouchers = this.voucherService.findAll();

        for (Voucher voucher : vouchers) {
            this.output.println(voucher.toString());
        }
    }

    private void printBlacklist() {
        this.output.println("===  [Blacklist]  ===");
        List<Customer> blacklist = this.blackListService.findAll();

        for (Customer user : blacklist) {
            this.output.println(user.toString());
        }
    }

    private void logAndPrintException(Exception exception) {
        this.logger.error(exception.getMessage());
        this.output.println(exception.getMessage());
    }
}
