package org.prgrms.part1.engine;

import org.prgrms.part1.engine.service.VoucherService;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

public class VoucherManager implements Runnable{
    private final Input input;
    private final Output output;
    private final Logger logger;
    private final VoucherCreator voucherCreator;
    private final VoucherPoster voucherPoster;
    private final CsvReader csvReader;

    public VoucherManager(VoucherService voucherService, Input input, Output output, Logger logger) {
        this.input = input;
        this.output = output;
        this.logger = logger;
        this.voucherCreator = new VoucherCreator(voucherService, input, output, logger);
        this.voucherPoster = new VoucherPoster(voucherService, output);
        this.csvReader = new CsvReader(output);
    }


    @Override
    public void run() {
        final String SELECT_EXIT = "exit";
        final String SELECT_CREATE = "create";
        final String SELECT_LIST = "list";
        final String SELECT_SHOW_BLACKLIST = "black";
        output.print("=== Voucher Program ===");
        logger.debug("Voucher program On");
        main:
        while (true) {
            try {
                var selection = selectMainMenu();
                switch (selection) {
                    case SELECT_EXIT:
                        logger.debug("User select exit");
                        logger.debug("Voucher program Off");
                        break main;
                    case SELECT_CREATE:
                        logger.debug("User select create");
                        voucherCreator.run();
                        break;
                    case SELECT_LIST:
                        logger.debug("User select list");
                        voucherPoster.run();
                        break;
                    case SELECT_SHOW_BLACKLIST:
                        logger.debug("User select show blacklist");
                        csvReader.run();
                        break;
                    default:
                        throw new VoucherException("Please select valid menu.");
                }
            } catch (VoucherException ex) {
                logger.error("Voucher Error! Print to User : " + ex.getMessage());
                output.printError(ex.getMessage());
            }
        }
    }

    private String selectMainMenu() {
        output.print("Type exit to exit the program.");
        output.print("Type create to create a new voucher.");
        output.print("Type list to list all vouchers.");
        output.print("Type black to show blacklist.");
        return input.select();
    }


}
