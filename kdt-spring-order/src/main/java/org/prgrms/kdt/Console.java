package org.prgrms.kdt;

import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.console.Command;
import org.prgrms.kdt.io.console.Validator;
import org.prgrms.kdt.io.file.IO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class Console {

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
        if (validator.isInValidCommand(cmd)) {
            io.writeLine("Invalid command, Try again");
            return Command.CONTINUE;
        }

        if (Command.valueOf(cmd) == Command.EXIT){
            io.writeLine("Bye");
            return Command.EXIT;
        }

        execute(cmd);
        return Command.CONTINUE;
    }

    private void execute(String cmd) throws IOException {
        if (Command.valueOf(cmd) == Command.CREATE) {
            printCreateMenu();
            if (createVoucher().isPresent()) {
                io.writeLine("Successfully create voucher");
                return;
            }
            io.writeLine("Invalid input, Try again");
        } else {
            printVouchers();
        }
    }

    private void printCreateMenu() throws IOException {
        io.writeLine("=== Create Voucher ==");
        io.writeLine("Enter the type of voucher");
        io.writeLine("Enter the amount of discount");
        io.writeLine("FixedAmountVoucher: 0, PercentAmountVoucher: 1");
        io.writeLine("Please separate the type and amount with a space");
        io.writeLine("Example: 0 10");
        io.writeLine("type: FixedAmountVoucher, amount: 10");
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
                        e.printStackTrace();
                    }
                });
    }

    private Optional<UUID> createVoucher() throws IOException {
        String[] line = io.readLine().trim().split(" ");

        if (validator.isNotDigitArray(line))
            return Optional.empty();

        int type = convertStrVoucherTypeToInt(line[0]);
        long value = convertStrVoucherValueToLong(line[1]);

        if (validator.isInValidType(type))
            return Optional.empty();

        if (validator.isInvalidValue(value))
            return Optional.empty();

        return Optional.of(voucherService.save(new RequestCreatVoucherDto(convertIntToVoucherType(type), value)));
    }

    private VoucherType convertIntToVoucherType(int type) {
        return VoucherType.findType(type);
    }

    private long convertStrVoucherValueToLong(String value) {
        return Long.parseLong(value);
    }

    private int convertStrVoucherTypeToInt(String type) {
        return Integer.parseInt(type);
    }

}
