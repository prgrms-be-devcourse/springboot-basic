package com.prgrms.devkdtorder.customer.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BlackCustomers implements Iterable<Customer> {

    private final List<Customer> customers;

    public static BlackCustomers valueOf(List<Customer> customers) {
        return new BlackCustomers(customers);
    }

    public static BlackCustomers empty() {
        return new BlackCustomers(new ArrayList<>());
    }

    private BlackCustomers(List<Customer> customers) {
        Objects.requireNonNull(customers);
        validateBlack(customers);
        this.customers = customers;
    }

    public int size() {
        return customers.size();
    }

    @Override
    public void forEach(Consumer<? super Customer> action) {
        for (Customer customer : customers) {
            action.accept(customer);
        }
    }

    @Override
    public Iterator<Customer> iterator() {
        return customers.iterator();
    }

    private void validateBlack(List<Customer> customers) {
        boolean existNonBlack = customers.stream().anyMatch(c -> !c.getCustomerType().isBlack());
        if (existNonBlack) {
            throw new IllegalArgumentException("고객 리스트에 블랙되지 않은 고객이 존재합니다.");
        }
    }
}
