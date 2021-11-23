package org.prgrms.kdt.domain.command.voucher;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

public class VoucherCreateCommandAction implements CommandAction {

    private final VoucherService voucherService;
    private final IO io;

    public VoucherCreateCommandAction(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void action() throws IOException {
        printCreateMenu();

        String[] line = readAndSplitLine();

        validate(line);

        int type = mapToInt(line[0]);
        long value = mapToLong(line[1]);

        voucherService.insert(mapToVoucherType(type), value);
    }

    private void printCreateMenu() throws IOException {
        io.writeLine("=== Create Voucher ==");
        io.writeLine("Enter the type of voucher");
        io.writeLine("Enter the amount of discount");
        io.writeLine("Fixed discount voucher: 0, Percent discount voucher: 1");
        io.writeLine("Please separate the type and amount with a space");
        io.writeLine("Example: 0 10");
        io.writeLine("type: Fixed discount voucher, amount: 10");
        io.writeLine("=====================");
    }

    private void validate(String[] line) {

        if (isNotDigit(line))
            throw new IllegalArgumentException("Wrong Input");

        int type = mapToInt(line[0]);
        long value = mapToLong(line[1]);

        if (VoucherType.isInvalidType(type)) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }


        if (VoucherType.isInvalidValueForType(value, mapToVoucherType(type))) {
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }

    private String[] readAndSplitLine() throws IOException {
        return io.readLine().trim().split(" ");
    }

    private VoucherType mapToVoucherType(int type) {
        return VoucherType.findType(type);
    }

    private long mapToLong(String value) {
        return Long.parseLong(value);
    }

    private int mapToInt(String type) {
        return Integer.parseInt(type);
    }

    private boolean isNotDigit(String[] strArray) {
        if (isEmpty(strArray))
            return true;

        for (String str : strArray) {
            if (isNotDigit(str)) return true;
        }

        return false;
    }

    private boolean isNotDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isNotDigit(str.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean isNotDigit(char ch) {
        return !Character.isDigit(ch);
    }

    private boolean isEmpty(String[] line) {
        return line.length != 2;
    }
}
