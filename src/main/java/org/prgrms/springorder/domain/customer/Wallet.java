package org.prgrms.springorder.domain.customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.voucher.model.Voucher;

public class Wallet {

    private Map<Customer, List<Voucher>> vouchers;

    public Wallet(Customer customer, List<Voucher> vouchers) {
        this.vouchers = new HashMap<>() {{
            put(customer, vouchers);
        }};

    }

    public List<Voucher> getVouchers() {
        Iterator<Entry<Customer, List<Voucher>>> iterator = vouchers.entrySet().iterator();
        Entry<Customer, List<Voucher>> entry = iterator.next();

        return entry.getValue();
    }

    public Customer getCustomer() {
        Iterator<Entry<Customer, List<Voucher>>> iterator = vouchers.entrySet().iterator();
        Entry<Customer, List<Voucher>> entry = iterator.next();

        return entry.getKey();
    }

    public boolean isEmpty() {
        return this.vouchers.isEmpty();
    }

    @Override
    public String toString() {

        if (isEmpty()) {
            throw new IllegalStateException("wallet is empty");
        }

        Iterator<Entry<Customer, List<Voucher>>> iterator = vouchers.entrySet().iterator();

        Entry<Customer, List<Voucher>> entry = iterator.next();

        Customer customer = entry.getKey();

        List<Voucher> vouchers = entry.getValue();

        return "Customer Wallet{ \n" +
            "customer = " + customer + "\n" +
            "voucher = {\n" + vouchers.stream().map(Voucher::toString)
            .collect(Collectors.joining(System.lineSeparator())) +
            " \n}";
    }
}
