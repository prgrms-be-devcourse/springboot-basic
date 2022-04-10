package org.prgrms.part1.engine;

import org.prgrms.part1.exception.FileException;
import org.prgrms.part1.exception.InputException;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class VoucherManager implements Runnable{
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;
    private final Logger logger;

    public VoucherManager(VoucherService voucherService, Input input, Output output, Logger logger) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
        this.logger = logger;
    }

    @Override
    public void run() {
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
                    case "exit":
                        logger.debug("User select exit");
                        logger.debug("Voucher program Off");
                        break main;
                    case ("create"):
                        logger.debug("User select create");
                        create:
                        while (true) {
                            VoucherType type;
                            output.print("Select type of Voucher");
                            output.print("1. Fixed Amount Voucher");
                            output.print("2. Percent Amount Voucher");
                            output.print("3. Back to Main");
                            var num = input.select();

                            switch (num) {
                                case "1":
                                    logger.debug("User select fixed amount Voucher");
                                    type = VoucherType.FixedAmount;
                                    break;
                                case "2":
                                    logger.debug("User select percent discount voucher");
                                    type = VoucherType.PercentDiscount;
                                    break;
                                case "3":
                                    logger.debug("User select back to main");
                                    break create;
                                default:
                                    logger.warn("User select invalid voucher");
                                    output.printError("Please Type Valid Number");
                                    continue;
                            }
                            var inputValue = input.question("Type discount amount(percent) of Voucher : ");
                            int value = parseValue(inputValue);
                            Voucher voucher = voucherService.createVoucher(type, value);
                            logger.info(MessageFormat.format("Create Voucher.\nVoucher Id: {0}\nVoucher Type: {1}\nVoucher Discount: {2}", voucher.getVoucherId(), voucher.getVoucherType()), voucher.getDiscount());
                        }
                        break;
                    case "list":
                        logger.debug("User select list");
                        if (voucherService.getAllVouchers().isEmpty()) {
                            output.print("Voucher List is empty!");
                            continue;
                        }

                        output.print("Show Voucher List\n");
                        voucherService.getAllVouchers().forEach(v -> {
                            output.print("Voucher Id : " + v.getVoucherId());
                            output.print("Voucher Type : " + v.getVoucherType());
                            output.print("Discount Amount : " + v.getDiscount());
                            output.print("");

                        });
                        break;
                    default:
                        logger.warn("User select invalid menu");
                        throw new InputException();
                }
            } catch (InputException ex) {
                output.printError("Please Type Valid Value");
            } catch (VoucherException ex) {
                logger.error("Voucher Error!");
                output.printError("Voucher Manager is Broken. Please Call Developer");
            } catch (FileException ex) {
                logger.error("Voucher File Error!");
                output.printError("Voucher File is Broken. Please Call Developer");
            }
        }
    }

    private int parseValue(String inputValue) {
        if (Pattern.matches("[\\d]+", inputValue)) {
            return Integer.parseInt(inputValue);
        } else {
            logger.warn("User type invalid Number");
            throw new InputException();
        }
    }
}
