package programmers.org.kdt.engine;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;
import programmers.org.kdt.engine.customer.CustomerService;
import programmers.org.kdt.engine.io.ConsoleIO;
import programmers.org.kdt.engine.voucher.type.Voucher;
import programmers.org.kdt.engine.voucher.VoucherService;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

//@Component
public class CommandLine implements Runnable {

    private boolean runState = true;
    private final ConsoleIO consoleIO;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLine(ConsoleIO consoleIO, VoucherService voucherService,
        CustomerService customerService) {
        this.voucherService = voucherService;
        this.consoleIO = consoleIO;
        this.customerService = customerService;
    }
    

    @Override
    public void run() {
        while (runState) {
            consoleIO.help();
            String command = consoleIO.input("Please Input Command : ");
            runState = checkRunState(command);
        }
    }

    public void showList(Set<Entry<UUID, Voucher>> entrySet) {
        // start of function
        consoleIO.print("=== show list all vouchers ===");

        // get list
        if (entrySet.isEmpty()) consoleIO.print("List is empty");
        entrySet.forEach(entry -> {
                Voucher voucher = entry.getValue();
                String type = VoucherStatus.getValueType(voucher.getVoucherStatus());

                consoleIO.print(
                    MessageFormat.format("UUID : {0} / Voucher value : {1}{2}",
                        entry.getKey(),
                        voucher.getValue(),
                        type)
                );
            }
        );

        // end of function
        consoleIO.print("=== end of list all vouchers ===");
    }

    private boolean runCreate() {
        //input Voucher type
        var inputVoucherType=consoleIO.input("Please input the type of Voucher");
        var voucherStatus= VoucherStatus.fromString(inputVoucherType);
        if(voucherStatus.equals(VoucherStatus.NULL)) {
            consoleIO.error("Wrong Voucher Status.");
            return false;
        }

        //input Voucher value
        var inputVoucherValue = consoleIO.input("Please input value of Voucher");
        Long voucherValue = getVoucherValue(inputVoucherValue);
        if (voucherValue == null) {
            return false;
        }

        // Create Voucher
        var voucher= voucherService.createVoucher(voucherStatus, voucherValue);
        if (voucher.isEmpty()) {
            consoleIO.error("Wrong Voucher value.");
            return false;
        }

        Voucher voucherData = voucher.get();
        consoleIO.print(
            MessageFormat.format(
                "Create success. [UUID :{0} / Value : {1}{2}]",
                voucherData.getVoucherId(),
                voucherData.getValue(),
                voucherData.getVoucherStatus().toValueType()
            )
        );
        return true;
    }

    private Long getVoucherValue(String inputVoucherValue) {
        Long voucherValue = 0L;
        try{
            if (!inputVoucherValue.isBlank()) {
                voucherValue = Long.parseLong(inputVoucherValue);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            consoleIO.error("Value is not number. (Long type)");
            return null;
        }
        return voucherValue;
    }

    private boolean checkRunState(String command) {
        RunStatus runStatusData = RunStatus.NULL;

        try {
            runStatusData = RunStatus.valueOf(command.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            consoleIO.print(MessageFormat.format("Wrong input : {0}", command));
        }

        boolean ret = true;
        switch (runStatusData) {
            case CREATE -> {
                if (!runCreate()) {
                    ret = false;
                }
            }
            case LIST -> showList(voucherService.getRepositoryEntry());
            case EXIT -> {
                ret = false;
                consoleIO.print("Input EXIT. Good Bye!");
            }
            default -> consoleIO.error("Wrong type command Error");
        }
        return ret;
    }
}
