package org.prgrms.kdt.domain.command.wallet;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.WalletService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletFindCommandAction implements CommandAction {

    private final WalletService walletService;
    private final  IO io;
    private final List<Customer> customers;
    private final List<Voucher> vouchers;

    public WalletFindCommandAction(WalletService walletService, IO io) {
        this.walletService = walletService;
        this.io = io;
        this.customers = walletService.allCustomers();
        this.vouchers = walletService.allVouchers();
    }

    @Override
    public void action() throws IOException, IllegalArgumentException {
        printSelectVoucherMenu();
        printVouchers();

        String line = readLine();
        validate(vouchers.size(), line);

        int voucherSeq = mapToInt(line);
        Voucher voucher = vouchers.get(voucherSeq);
        List<Customer> customersHavingVoucher = walletService.findCustomersHavingVoucher(voucher.getVoucherId());

        printCustomers(customersHavingVoucher);

    }

    private void printCustomers(List<Customer> customersHavingVoucher) throws IOException {
        for (Customer customer : customersHavingVoucher) {
            printCustomer(customer);
        }
    }

    private void printCustomer(Customer customer) throws IOException {
        io.writeLine(MessageFormat.format("Name: {0} | Email: {1} | Created: {2}", customer.getName(), customer.getEmail(), customer.getCreatedAt()));
    }

    private void printSelectVoucherMenu() throws IOException {
        io.writeLine("=== Select Voucher ==");
        io.writeLine("Enter the sequence of voucher");
        io.writeLine("Example: 0");
        io.writeLine("=====================");
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
        return !matcher.matches();
    }

    private int mapToInt(String type) {
        return Integer.parseInt(type);
    }
    private String readLine() throws IOException {
        return io.readLine();
    }
}
