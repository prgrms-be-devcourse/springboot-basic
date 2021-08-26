package org.prgrms.kdt;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.console.Command;
import org.prgrms.kdt.io.console.Validator;
import org.prgrms.kdt.io.file.IO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.io.console.Command.*;

@Component
public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final IO io;
    private final VoucherService voucherService;
    private final Validator validator = new Validator();

    @Autowired
    public Console(@Qualifier("consoleIo") IO io, VoucherService voucherService) {
        this.io = io;
        this.voucherService = voucherService;
    }

    public Command run() throws IOException {
        printInitMenu();
        String cmd = io.readLine().trim().toUpperCase();
        
        if (Command.isInvalidCommand(cmd)) {
            logger.warn(MessageFormat.format("Invalid command at {0}: cmd = {1}", this.getClass().getSimpleName(), cmd));
            io.writeLine("Invalid command, Try again");
            return Command.CONTINUE;
        }
        Command command = Command.valueOf(cmd);
        execute(command);
        return command;
    }

    private void execute(Command command) throws IOException {
        switch (command) {
            case EXIT -> {
                io.writeLine("Bye");
            }
            case CREATE -> {
                printCreateMenu();
                if (createVoucher().isPresent()) {
                    io.writeLine("Successfully create voucher");
                    return;
                }
                logger.warn(MessageFormat.format("Invalid input at {0}: command = {1}", this.getClass().getSimpleName(), command));
                io.writeLine("Invalid input, Try again");
            }
            case LIST -> {
                printVouchers();
            }
        }
    }

    private void printCreateMenu() throws IOException {
        io.writeLine("=== Create Voucher ==");
        io.writeLine("Enter the type of voucher");
        io.writeLine("Enter the amount of discount");
        io.writeLine("Fixed discount voucher: 0, Percent discount voucher: 1");
        io.writeLine("Please separate the type and amount with a space");
        io.writeLine("Example: 0 10");
        io.writeLine("type: Fixed discount voucher, amount: 10");
        io.writeLine("=====================");
    }

    private void printInitMenu() throws IOException {
        io.writeLine("=== Voucher Program ===");
        io.writeLine("Type exit to exit the program");
        io.writeLine("Type create to create a new voucher");
        io.writeLine("Type List to list all vouchers");
    }

    private void printVouchers() {
        voucherService.vouchers()
                .forEach(v -> {
                    try {
                        io.writeLine(v.toString());
                    } catch (IOException e) {
                        logger.error(MessageFormat.format("Exception at {0}: {1}", this.getClass().getSimpleName(), e.getMessage()));
                        e.printStackTrace();
                    }
                });
    }

    private Optional<UUID> createVoucher() throws IOException {
        String[] line = io.readLine().trim().split(" ");

        if (validator.isNotDigit(line))
            return Optional.empty();

        int type = mapToInt(line[0]);
        long value = mapToLong(line[1]);

        if (VoucherType.isInvalidType(type)) {
            logger.warn(MessageFormat.format("Invalid input at {0}: type = {1}", this.getClass().getSimpleName(), type));
            return Optional.empty();
        }

        if (VoucherType.isInvalidValueForType(value, mapToVoucherType(type))) {
            logger.warn(MessageFormat.format("Invalid value at {0}: value={1}, type={2}", this.getClass().getSimpleName(), type, value));
            return Optional.empty();
        }

        return Optional.of(voucherService.save(new RequestCreatVoucherDto(mapToVoucherType(type), value)));
    }

    private VoucherType mapToVoucherType(int type) {
        return VoucherType.findType(type);
    }

    private long mapToLong(String value) {
        return Long.parseLong(value);
    }

    private int mapToInt(String type) {
        return Integer.parseInt(type);
    }

}
