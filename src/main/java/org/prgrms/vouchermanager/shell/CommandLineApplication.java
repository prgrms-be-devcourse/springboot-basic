package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.console.Console;
import org.prgrms.vouchermanager.voucher.VoucherService;
import org.prgrms.vouchermanager.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandLineApplication implements VoucherManagerShell {

    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Console console;
    private final VoucherService voucherService;
    private boolean initialized = false;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        init();
        // TODO: command, CommandType으로 변경
        while (true) {
            String command = console.read();
            if (command.equalsIgnoreCase("exit")) return;
            if (command.equalsIgnoreCase("create")) {
                createVoucher();
                continue;
            }
            if (command.equalsIgnoreCase("list")) printVoucherLists();
        }
    }

    private void init() {
        if (initialized) return;
        initialized = true;
        console.print("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
    }

    private void createVoucher() {
        VoucherType voucherType = getInputVoucherType();
        Long discountAmount = getInputAmount();
        voucherService.createVoucher(voucherType, discountAmount);
    }

    private VoucherType getInputVoucherType() {
        Optional<VoucherType> voucherType = VoucherType.findVoucherType(console.read());
        return voucherType.orElse(VoucherType.FIXED);
    }

    private Long getInputAmount() {
        return console.readLong();
    }

    private void printVoucherLists() {
        console.print(voucherService.allVouchersToString());
    }

}


