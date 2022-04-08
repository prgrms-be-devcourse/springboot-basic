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
        return Command.findCommand(console.read());
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
        Long discountAmount = readDiscountAmount();
        voucherService.createVoucher(voucherType, discountAmount);
    }

    private VoucherType getInputVoucherType() {
        while (true) {
            console.println("voucher type 입력");
            VoucherType voucherType = VoucherType.findVoucherType(console.read());
            if (voucherType == VoucherType.INVALID) continue;
            return voucherType;
        }
    }

    private Long readDiscountAmount() {
        while (true) {
            console.println("1~100 사이의 할인율을 입력하세요.");
            Long amount = console.readLong();
            if (amount <= 0 || amount > 100) continue;
            return amount;
        }
    }

    private void printVoucherLists() {
        console.print(voucherService.allVouchersToString());
    }

}


