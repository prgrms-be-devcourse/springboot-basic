package programmers.org.kdt.engine;

import programmers.org.kdt.engine.io.Console;
import programmers.org.kdt.engine.voucher.VoucherService;
import programmers.org.kdt.engine.voucher.VoucherStatus;

public class CommandLine implements Runnable {

    private boolean runState = true;
    private final Console console;
    private final VoucherService voucherService;

    public CommandLine(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {

        while (runState) {
            console.help();
            var command = console.input("Please Input Command : ");

            if (command.equalsIgnoreCase(String.valueOf(RunStatus.CREATE))) {
                var voucher = voucherService.createVoucher(VoucherStatus.FixedAmountVoucher, 10L);
                if(voucher.isEmpty()) {console.error();}
            } else if (command.equalsIgnoreCase(String.valueOf(RunStatus.LIST))) {
                var entry = voucherService.getRepositoryEntry();
                console.list(entry);
            } else if (command.equalsIgnoreCase(String.valueOf(RunStatus.EXIT))) {
                runState = false;
            } else {
                console.error();
            }
        }

    }
}
