package org.prgrms.kdt.domain.command.wallet;

import org.prgrms.kdt.domain.command.CommandAction;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.wallet.Wallet;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.WalletService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WalletListCommandAction implements CommandAction {

    private final WalletService walletService;
    private final IO io;
    private final List<Customer> customers;

    public WalletListCommandAction(WalletService walletService, IO io) {
        this.walletService = walletService;
        this.io = io;
        this.customers = walletService.allCustomers();
    }

    @Override
    public void action() throws IOException, IllegalArgumentException {
        printSelectCustomerMenu();
        printCustomers();

        String line = readLine();
        validate(customers.size(), line);

        Customer customer = customers.get(mapToInt(line));
        printVouchers(getVouchers(customer));
    }

    private void printVouchers(List<Voucher> vouchers) throws IOException {
        for (Voucher voucher : vouchers) {
            printVoucher(voucher);
        }
    }

    private void printVoucher(Voucher voucher) throws IOException {
        io.writeLine(MessageFormat.format("Type: {0} Value: {1}", voucher.getType(), voucher.getValue()));
    }

    private List<Voucher> getVouchers(Customer customer) {
        Wallet customerWallet = walletService.findCustomerWallet(customer.getCustomerId());
        return customerWallet.getVoucherIds()
                .stream()
                .map(voucherId -> walletService.findVoucher(voucherId))
                .collect(Collectors.toList());
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

    private String readLine() throws IOException {
        return io.readLine();
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
}
