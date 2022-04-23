package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.customer.CustomerGrade;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Collectors;

public class CommandLineRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    private CommandType commandType;

    public CommandLineRunner(
        Input input,
        Output output,
        VoucherService voucherService,
        CustomerService customerService
    ) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {

        String command;
        commandType = CommandType.INVALID;
        while (commandType != CommandType.EXIT) {
            output.printCommandManual();

            command = input.input();
            commandType = CommandType.getCommandType(command);
            switch (commandType) {
                case EXIT -> exitCommandRunner();
                case CREATE -> createVoucher();
                case UPDATE -> updateVoucher();
                case DELETE -> deleteVoucher();
                case LIST -> findVoucherList();
                case BLACK_LIST -> findBlackList();
                default -> handleInValidCommand();
            }
        }
    }

    private void exitCommandRunner() {
        commandType = CommandType.EXIT;
        output.printShutDownSystem();
    }

    private void handleInValidCommand() {
        output.printInvalidCommand();
    }

    private void createVoucher() {
        output.printVoucherType();

        try {
            VoucherType voucherType = VoucherType.getVoucherType(input.input());

            output.printVoucherValue(voucherType);
            long voucherValue = input.inputLong();

            Voucher voucher = voucherService.createVoucher(UUID.randomUUID(), voucherValue, voucherType);
            output.printVoucherCreateSuccess(voucher.toString());
        } catch (IllegalArgumentException e) {
            logger.warn("[Voucher] create error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void updateVoucher() {
        output.printVoucherUpdateManual();

        try {
            UUID voucherId = UUID.fromString(input.input());

            output.printVoucherUpdateValue();
            long voucherValue = input.inputLong();

            Voucher voucher = voucherService.updateVoucherValue(voucherId, voucherValue);
            output.printVoucherUpdateSuccess(voucher.toString());
        } catch (RuntimeException e) {
            logger.warn("[Voucher] update error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void deleteVoucher() {
        output.printVoucherDeleteManual();

        try {
            UUID voucherId = UUID.fromString(input.input());

            voucherService.deleteVoucher(voucherId);
            output.printVoucherDeleteSuccess();
        } catch (RuntimeException e) {
            logger.warn("[Voucher] delete error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void findVoucherList() {
        String vouchers = voucherService.findAll().stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));
        output.printMessage(vouchers);
    }

    private void findBlackList() {
        String blackListCustomers = customerService.findAllBlackList(CustomerGrade.BLACK_LIST).stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));
        output.printMessage(blackListCustomers);
    }
}
