package org.prgrms.part1.engine;

import org.prgrms.part1.engine.enumtype.VoucherMenu;
import org.prgrms.part1.engine.service.CustomerService;
import org.prgrms.part1.engine.service.VoucherService;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

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
                var selection = selectMainMenu();
                Optional<VoucherMenu> menu = VoucherMenu.findMatchingMenu(selection);
                if (menu.isEmpty()) {
                    logger.debug("User type invalid command.");
                    throw new VoucherException("Please type valid command");
                }
                Boolean termination = menu.get().runMethod(voucherFunction);
                if (termination) {
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
        output.print("Type exit to exit the program.");
        return input.select();
    }


}
