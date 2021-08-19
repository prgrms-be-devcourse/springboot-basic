package programmers.org.kdt.engine.io;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import programmers.org.kdt.engine.io.Input;
import programmers.org.kdt.engine.io.Output;
import programmers.org.kdt.engine.voucher.Voucher;

import java.util.Scanner;
import programmers.org.kdt.engine.voucher.VoucherStatus;

public class Console_terminal implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    //input
    @Override
    public String input(String command) {
        System.out.println(command);
        return scanner.nextLine();
    }

    //output
    @Override
    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
    }

    @Override
    public void list(Set<Map.Entry<UUID, Voucher>> entrySet) {
        System.out.println("=== show list all vouchers ===");

        Iterator<Map.Entry<UUID,Voucher>> iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Map.Entry<UUID, Voucher> e = iterator.next();
            Voucher voucher = e.getValue();
            var status= voucher.getVoucherStatus();
            String type;
            if(status == VoucherStatus.FixedAmountVoucher) type = "$";
            else if(status == VoucherStatus.PercentDiscountVoucher) type = "%";
            else type = "error";

            System.out.println(MessageFormat.format("UUID : {0} / Voucher value : {1}{2}", e.getKey(), voucher.getValue(), type));
        }

        System.out.println("=== end of list all vouchers ===");
    }

    @Override
    public void error() {
        System.out.println("Please try again");
    }

    public void close() {

    }
}
