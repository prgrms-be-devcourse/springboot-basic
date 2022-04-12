package com.prgms.management.command.io;

import com.prgms.management.customer.model.Customer;
import com.prgms.management.voucher.entity.FixedAmountVoucher;
import com.prgms.management.voucher.entity.PercentDiscountVoucher;
import com.prgms.management.voucher.entity.Voucher;
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
        printString("Type **percent** to create a percent discount voucher.");
        printString("Type **fixed** to create a fixed amount voucher.");
        printString("");

        String command = textIO.newStringInputReader()
                .withDefaultValue("fixed")
                .read("Voucher type");
        switch (command.toLowerCase()) {
            case "fixed":
                Long amount = textIO.newLongInputReader()
                        .withMinVal(FixedAmountVoucher.MIN_AMOUNT)
                        .withMaxVal(FixedAmountVoucher.MAX_AMOUNT)
                        .read("Amount");
                return new FixedAmountVoucher(amount);
            case "percent":
                Integer percent = textIO.newIntInputReader()
                        .withMinVal(PercentDiscountVoucher.MIN_PERCENT)
                        .withMaxVal(PercentDiscountVoucher.MAX_PERCENT)
                        .read("Percent");
                return new PercentDiscountVoucher(percent);
            default:
                return null;
        }
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
