package programmers.org.kdt.engine.io;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import programmers.org.kdt.engine.io.Input;
import programmers.org.kdt.engine.io.Output;
import programmers.org.kdt.engine.voucher.Voucher;

import java.util.Scanner;
import programmers.org.kdt.engine.voucher.VoucherStatus;

public class Console implements Input, Output {
    //private final Scanner scanner = new Scanner(System.in);
    TextIO textIO = TextIoFactory.getTextIO();

    //input
    @Override
    public String input(String command) {
        return textIO.newStringInputReader().read(command);
    }

    //output
    @Override
    public void help() {
        textIO.getTextTerminal().println("=== Voucher Program ===");
        textIO.getTextTerminal().println("Type **exit** to exit the program.");
        textIO.getTextTerminal().println("Type **create** to create a new voucher.");
        textIO.getTextTerminal().println("Type **list** to list all vouchers.");
    }

    @Override
    public void list(Set<Map.Entry<UUID, Voucher>> entrySet) {
        textIO.getTextTerminal().println("=== show list all vouchers ===");

        Iterator<Map.Entry<UUID,Voucher>> iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Map.Entry<UUID, Voucher> e = iterator.next();
            Voucher voucher = e.getValue();
            var status= voucher.getVoucherStatus();
            String type;
            if(status == VoucherStatus.FixedAmountVoucher) type = "$";
            else if(status == VoucherStatus.PercentDiscountVoucher) type = "%";
            else type = "error";

            //System.out.println(MessageFormat.format("UUID : {0} / Voucher value : {1}{2}", e.getKey(), voucher.getValue(), type));
            textIO.getTextTerminal().println(MessageFormat.format("UUID : {0} / Voucher value : {1}{2}", e.getKey(), voucher.getValue(), type));
        }

        textIO.getTextTerminal().println("=== end of list all vouchers ===");
    }

    @Override
    public void error() {
        //System.out.println("Please try again");
        textIO.getTextTerminal().println("Please try again.");
    }

    public void close() {
        textIO.dispose();
    }
}
