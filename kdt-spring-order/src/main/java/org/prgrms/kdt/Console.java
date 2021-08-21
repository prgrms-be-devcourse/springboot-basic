package org.prgrms.kdt;

import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.OutPut;
import org.prgrms.kdt.io.Validator;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class Console {

    public static final int CONTINUE = 1;
    public static final int EXIT = 0;

    private final Input input;
    private final OutPut output;
    private final VoucherService voucherService;
    private final Validator validator;

    public Console(Input input, OutPut output, VoucherService voucherService, Validator validator) {
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
            return CONTINUE;
        }

        if (cmd.equals(Validator.EXIT)) {
            System.out.println("Bye");
            return EXIT;
        }

        execute(cmd);
        return CONTINUE;
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
        return Optional.of(voucherService.save(new RequestCreatVoucherDto(mapToVoucherType(line[0]), Long.parseLong(line[1]))));
    }

    private VoucherType mapToVoucherType(String s) {
        return VoucherType.findType(Integer.parseInt(s));
    }
}
