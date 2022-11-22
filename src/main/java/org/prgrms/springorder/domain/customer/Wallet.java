package org.prgrms.springorder.domain.customer;

import java.util.List;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.voucher.model.Voucher;

public class Wallet {

    private final Customer customer;

    private final List<Voucher> vouchers;

    public Wallet(Customer customer, List<Voucher> vouchers) {
        this.customer = customer;
        this.vouchers = vouchers;
    }

    public List<Voucher> getVouchers() {
        return this.vouchers;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public boolean isEmpty() {
        return this.vouchers.isEmpty();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            throw new IllegalStateException("wallet is empty");
        }

        return "Customer Wallet{ \n" +
            "customer = " + customer + "\n" +
            "voucher = {\n" + vouchers.stream().map(Voucher::toString)
            .collect(Collectors.joining(System.lineSeparator())) +
            " \n}";
    }
}
