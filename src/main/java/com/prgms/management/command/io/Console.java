package com.prgms.management.command.io;

import com.prgms.management.command.exception.CommandLineException;
import com.prgms.management.customer.entity.Customer;
import com.prgms.management.voucher.entity.FixedAmountVoucher;
import com.prgms.management.voucher.entity.PercentDiscountVoucher;
import com.prgms.management.voucher.entity.Voucher;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console implements Input, Output<Voucher> {
    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public CommandType getCommandType() throws CommandLineException {
        textIO.getTextTerminal().println("=========== Voucher Program ===========");
        textIO.getTextTerminal().println("Type **exit** to exit the program.");
        textIO.getTextTerminal().println("Type **create** to create a new voucher.");
        textIO.getTextTerminal().println("Type **list** to list all vouchers.");
        textIO.getTextTerminal().println("Type **blacklist** to list all black customers.");
        textIO.getTextTerminal().println();

        String command = textIO.newStringInputReader()
                .withDefaultValue("list")
                .read("Command");

        switch (command.toLowerCase()) {
            case "list":
                return CommandType.LIST;
            case "create":
                return CommandType.CREATE;
            case "exit":
                return CommandType.EXIT;
            case "blacklist":
                return CommandType.BLACKLIST;
            default:
                throw new CommandLineException();
        }
    }

    @Override
    public Voucher getVoucher() throws CommandLineException {
        textIO.getTextTerminal().println("=================== Create Voucher ===================");
        textIO.getTextTerminal().println("Type **percent** to create a percent discount voucher.");
        textIO.getTextTerminal().println("Type **fixed** to create a fixed amount voucher.");
        textIO.getTextTerminal().println();

        String command = textIO.newStringInputReader()
                .withDefaultValue("fixed")
                .read("Voucher type");
        switch (command.toLowerCase()) {
            case "fixed":
                Long amount = textIO.newLongInputReader()
                        .withMinVal(0L)
                        .read("Amount");
                return new FixedAmountVoucher(amount);
            case "percent":
                Integer percent = textIO.newIntInputReader()
                        .withMinVal(0)
                        .withMaxVal(100)
                        .read("Percent");
                return new PercentDiscountVoucher(percent);
            default:
                throw new CommandLineException();
        }
    }

    @Override
    public void printListCustomer(List<Customer> list) {
        for (Customer customer : list) {
            textIO.getTextTerminal().println(customer.toString());
        }
    }

    @Override
    public void printListVoucher(List<Voucher> list) {
        for (Voucher voucher : list) {
            textIO.getTextTerminal().println(voucher.toString());
        }
    }

    @Override
    public void printOneVoucher(Voucher voucher) {
        textIO.getTextTerminal().println("CREATE >> " + voucher.toString());
    }

    @Override
    public void printString(String str) {
        textIO.getTextTerminal().println(str);
    }

    public void close() {
        textIO.dispose();
    }
}
