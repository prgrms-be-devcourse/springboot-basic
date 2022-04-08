package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.console.Console;
import org.prgrms.vouchermanager.voucher.VoucherService;
import org.prgrms.vouchermanager.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements VoucherManagerShell {

    // 아직 사용하지 않는 로거
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

        while (true) {
            switch (getCommand()) {
                case EXIT -> {
                    return;
                }
                case CREATE -> createVoucher();
                case LIST -> printVoucherLists();
                case INVALID_COMMAND -> console.println("잘못된 커맨드, 다시 입력받기");
            }
        }

    }

    private Command getCommand() {
        return Command.findCommand(console.read()).orElse(Command.INVALID_COMMAND);
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
        console.println("voucher type 입력");
        VoucherType voucherType = getInputVoucherType();
        console.println("할인율 입력");
        Long discountAmount = getInputAmount();
        voucherService.createVoucher(voucherType, discountAmount);
    }

    private VoucherType getInputVoucherType() {
        return VoucherType.findVoucherType(console.read())
                .orElse(VoucherType.INVALID);
    }

    private Long getInputAmount() {
        return console.readLong();
    }

    private void printVoucherLists() {
        console.print(voucherService.allVouchersToString());
    }

}


