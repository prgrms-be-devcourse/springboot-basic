package org.prgrms.kdt.command;

import java.util.Map;
import java.util.UUID;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/20 Time: 7:03 오후
 */
public class ListCommand implements Command {

    private Map<UUID, Voucher> vouchers;
    private final Console console;

    public ListCommand(Map<UUID, Voucher> vouchers, Console console) {
        this.vouchers = vouchers;
        this.console = console;
    }

    @Override
    public void execute() {
        console.printVouchers(vouchers);
    }
}
