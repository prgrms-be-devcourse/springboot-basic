package org.prgrms.part1.engine;

import org.prgrms.part1.exception.FileException;
import org.prgrms.part1.exception.InputException;
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

    public VoucherManager(VoucherService voucherService, Input input, Output output, Logger logger) {
        this.input = input;
        this.output = output;
        this.logger = logger;
        this.voucherCreator = new VoucherCreator(voucherService, input, output, logger);
        this.voucherPoster = new VoucherPoster(voucherService, output, logger);
    }


    @Override
    public void run() {
        final String SELECT_EXIT = "exit";
        final String SELECT_CREATE = "create";
        final String SELECT_LIST = "list";
        output.print("=== Voucher Program ===");
        logger.debug("Voucher program On");
        main:
        while (true) {
            try {
                output.print("Type exit to exit the program.");
                output.print("Type create to create a new voucher.");
                output.print("Type list to list all vouchers.");
                var selection = input.select();
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
                    default:
                        throw new InputException("Please select valid menu.");
                }
            } catch (InputException ex) {
                logger.warn("Input Error! Print to User : " + ex.getMessage());
                output.printError(ex.getMessage());
            } catch (VoucherException ex) {
                logger.error("Voucher Error! Print to User : " + ex.getMessage());
                output.printError(ex.getMessage());
            } catch (FileException ex) {
                logger.error("Voucher File Error! Print to User : " + ex.getMessage());
                output.printError(ex.getMessage());
            }
        }
    }


}
