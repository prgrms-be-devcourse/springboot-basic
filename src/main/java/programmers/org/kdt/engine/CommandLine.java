package programmers.org.kdt.engine;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import programmers.org.kdt.engine.io.ConsoleIO;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.VoucherService;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

public class CommandLine implements Runnable {

    private boolean runState = true;
    private final ConsoleIO console;
    private final VoucherService voucherService;

    public CommandLine(VoucherService voucherService, ConsoleIO console) {
        this.voucherService = voucherService;
        this.console = console;
    }

    @Override
    public void run() {
        while (runState) {
            console.help();
            String command = console.input("Please Input Command : ");
            runState = checkRunState(command);
        }
    }

    public void showList(Set<Entry<UUID, Voucher>> entrySet) {
        // start of function
        console.print("=== show list all vouchers ===");

        // get list
        entrySet.forEach(entry -> {
                Voucher voucher = entry.getValue();
                String type = VoucherStatus.getValueType(voucher.getVoucherStatus());

                console.print(
                    MessageFormat.format("UUID : {0} / Voucher value : {1}{2}",
                        entry.getKey(),
                        voucher.getValue(),
                        type)
                );
            }
        );

        // end of function
        console.print("=== end of list all vouchers ===");
    }

    private boolean runCreate() {
        var voucher= voucherService.createVoucher(VoucherStatus.FixedAmountVoucher, 10L);
        if (voucher.isEmpty()) {
            console.error();
            return false;
        }
        return true;
    }

    private boolean checkRunState(String command) {
        RunStatus runStatusData = RunStatus.NULL;

        try {
            runStatusData = RunStatus.valueOf(command.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            console.print(MessageFormat.format("Wrong input : {0}", command));
        }

        boolean ret = true;
        switch (runStatusData) {
            case CREATE -> {
                if (!runCreate()) {
                    ret = false;
                }
            }
            case LIST -> showList(voucherService.getRepositoryEntry());
            case EXIT -> ret = false;
            default -> console.error();
        }
        return ret;
    }
}
