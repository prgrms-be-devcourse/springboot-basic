package org.prgrms.springbootbasic.engine;

import org.prgrms.springbootbasic.engine.enumtype.VoucherMenu;
import org.prgrms.springbootbasic.engine.service.CustomerService;
import org.prgrms.springbootbasic.engine.service.VoucherService;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.prgrms.springbootbasic.io.Input;
import org.prgrms.springbootbasic.io.Output;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.Optional;

public class VoucherManager implements Runnable{
    private final Input input;
    private final Output output;
    private final Logger logger;
    private final VoucherFunction voucherFunction;
    private final CsvReader csvReader;

    public VoucherManager(VoucherService voucherService, CustomerService customerService, Input input, Output output, Logger logger) {
        this.input = input;
        this.output = output;
        this.logger = logger;
        this.voucherFunction = new VoucherFunction(voucherService, customerService, input, output, logger);
        this.csvReader = new CsvReader(output);
    }

    @Override
    public void run() {
        output.print("=== Voucher Program ===");
        logger.debug("Voucher program On");
        while (true) {
            try {
                String selection = selectMainMenu();
                VoucherMenu menu;
                try {
                    menu = VoucherMenu.valueOf(selection.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    logger.debug("User type invalid command.");
                    throw new VoucherException("Please type valid command");
                }
                if (menu.runMethod(voucherFunction)) {
                    logger.debug("User select exit.");
                    logger.debug("Voucher Program Off");
                    break;
                }
            } catch (VoucherException ex) {
                logger.error("Voucher Exception! Print to user : " + ex.getMessage());
                output.printError(ex.getMessage());
            }
        }
    }

    private String selectMainMenu() {
        output.print("Type create to create a new voucher or new customer.");
        output.print("Type list to list all vouchers or all customers.");
        output.print("Type allocate to allocate voucher to customer.");
        output.print("Type deallocate to deallocate voucher to customer.");
        output.print("Type search to show detail about voucher and customer.");
        output.print("Type exit to exit the program.");
        return input.select();
    }


}
