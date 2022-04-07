package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.console.Console;
import org.prgrms.vouchermanager.voucher.VoucherService;
import org.prgrms.vouchermanager.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoucherManagerShellImpl implements VoucherManagerShell {

    private final Console console;
    private final VoucherService voucherService;
    private boolean initialized = false;

    public VoucherManagerShellImpl(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {

        init();

        while (true) {
            String command = console.read();
            if (command.equalsIgnoreCase("exit")) return;
            if (command.equalsIgnoreCase("create")) createVoucher();
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


