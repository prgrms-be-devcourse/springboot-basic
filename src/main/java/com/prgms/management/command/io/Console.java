package com.prgms.management.command.io;

import com.prgms.management.command.exception.CommandLineException;
import com.prgms.management.customer.model.Customer;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.entity.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Console implements Input, Output<Voucher> {
    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getCommand() {
        printString("=========== Voucher Program ===========");
        Arrays.stream(CommandType.values()).forEach(value -> printString(value.getScript()));
        printString("");
        return textIO.newStringInputReader()
                .withDefaultValue("list")
                .read("Command");
    }

    @Override
    public Voucher getVoucher() {
        printString("=================== Create Voucher ===================");
        Arrays.stream(VoucherType.values()).forEach(value -> printString(value.getScript()));
        printString("");

        String command = textIO.newStringInputReader()
                .withDefaultValue("fixed")
                .read("Voucher type").toLowerCase();

        VoucherType voucherType = VoucherType.of(command);

        if (voucherType == null) {
            throw new CommandLineException();
        }

        Integer paramNum = textIO.newIntInputReader()
                .read(voucherType.getNextCommand());

        return voucherType.createVoucher(paramNum);
    }

    @Override
    public void printListCustomer(List<Customer> list) {
        for (Customer customer : list) {
            printString(customer.toString());
        }
    }

    @Override
    public void printListVoucher(List<Voucher> list) {
        if (list.isEmpty()) {
            printString("저장된 데이터가 없습니다.");
        } else {
            for (Voucher voucher : list) {
                printString(voucher.toString());
            }
        }
    }

    @Override
    public void printOneVoucher(Voucher voucher) {
        printString("CREATE >> " + voucher.toString());
    }

    @Override
    public void printString(String str) {
        textIO.getTextTerminal().println(str);
    }

    public void close() {
        textIO.dispose();
    }
}
