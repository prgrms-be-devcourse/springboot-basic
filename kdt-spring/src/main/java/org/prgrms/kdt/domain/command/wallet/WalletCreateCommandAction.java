package org.prgrms.kdt.domain.command.wallet;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.WalletService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletCreateCommandAction implements CommandAction {
    private final WalletService walletService;
    private final IO io;
    private final List<Customer> customers;
    private final List<Voucher> vouchers;

    public WalletCreateCommandAction(WalletService walletService, IO io) {
        this.walletService = walletService;
        this.io = io;
        this.customers = walletService.allCustomers();
        this.vouchers = walletService.allVouchers();
    }

    @Override
    public void action() throws IOException {
        printSelectCustomerMenu();
        printCustomers();

        String line = readLine();
        validate(customers.size(), line);

        int customerSeq = mapToInt(line);

        printSelectVoucherMenu();
        printVouchers();

        line = readLine();
        validate(vouchers.size(), line);

        int voucherSeq = mapToInt(line);


        assignVoucher(customerSeq, voucherSeq);

    }

    private void assignVoucher(int customerSeq, int voucherSeq) {
        Customer customer = customers.get(customerSeq);
        Voucher voucher = vouchers.get(voucherSeq);

        walletService.assignVoucher(customer.getCustomerId(), voucher.getVoucherId());
    }

    private void printVouchers() throws IOException {
        for (int i = 0; i < customers.size(); i++) {
            printVoucherWithSequence(i);
        }
    }

    private void printVoucherWithSequence(int seq) throws IOException {
        Voucher voucher = vouchers.get(seq);
        io.writeLine(MessageFormat.format("Sequence: {0} | Type: {1} | Value: {2}", seq, voucher.getType(), voucher.getValue()));
    }

    private String readLine() throws IOException {
        return io.readLine();
    }

    private void printCustomers() throws IOException {
        for (int i = 0; i < customers.size(); i++) {
            printCustomerWithSequence(i);
        }
    }

    private void printCustomerWithSequence(int seq) throws IOException {
        Customer customer = customers.get(seq);
        io.writeLine(MessageFormat.format("Sequence: {0} | Name: {1} | Email: {2} | Created: {3}", seq, customer.getName(), customer.getEmail(), customer.getCreatedAt()));
    }

    private void printSelectCustomerMenu() throws IOException {
        io.writeLine("=== Select Customer to assign Voucher ==");
        io.writeLine("Enter the sequence of customer");
        io.writeLine("Example: 0");
        io.writeLine("=====================");
    }

    private void printSelectVoucherMenu() throws IOException {
        io.writeLine("=== Select Voucher ==");
        io.writeLine("Enter the sequence of voucher");
        io.writeLine("Example: 0");
        io.writeLine("=====================");
    }

    private void validate(int limit, String line) {

        if (line.isEmpty())
            throw new IllegalArgumentException("Wrong Input");
        if (isNotNumber(line))
            throw new IllegalArgumentException("Wrong Input");

        if (isOverLimit(limit, mapToInt(line)))
            throw new IllegalArgumentException("Wrong Input");
    }

    private boolean isOverLimit(int limit, int input) {
        return input >= limit;
    }

    private boolean isNotNumber(String line) {
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        System.out.println("matcher.matches() = " + matcher.matches());
        return !matcher.matches();
    }

    private int mapToInt(String type) {
        return Integer.parseInt(type);
    }
}