package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public class Console {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final Validator validator;

    public Console(Input input, Output output, VoucherService voucherService, Validator validator) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.validator = validator;
    }

    public int run() throws IOException {
        output.printInitMenu();
        String cmd = input.readLine().trim().toLowerCase();
        if (validator.isInValidCommand(cmd)) {
            System.out.println("Invalid command, Try again");
            return 0;
        }

        if (cmd.equals(Validator.EXIT)) {
            System.out.println("Bye");
            return 1;
        }

        execute(cmd);
        return 0;
    }

    private void execute(String cmd) throws IOException {
        if (cmd.equals(Validator.CREATE)) {
            output.printCreateMenu();
            if (createVoucher().isPresent()) {
                System.out.println("Successfully create voucher");
                return;
            }
            System.out.println("Invalid input, Try again");
        } else {
            printVouchers();
        }
    }

    private void printVouchers() {
        voucherService.vouchers()
                .forEach(v -> output.printLine(v.toString()));
    }

    private Optional<UUID> createVoucher() throws IOException {
        String[] line = input.readLine().trim().split(" ");
        if (validator.isInValidVoucherTypeAmount(line)) {
            return Optional.empty();
        }
        return Optional.of(voucherService.save(new RequestCreatVoucherDto(Integer.parseInt(line[0]), Long.parseLong(line[1]))));
    }
}
