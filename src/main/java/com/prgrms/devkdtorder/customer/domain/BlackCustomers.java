package com.prgrms.devkdtorder.customer.domain;

import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Consumer;

public class BlackCustomers {

    private final List<Customer> customers;

    private BlackCustomers(List<Customer> customers) {
        Objects.requireNonNull(customers);
        validateBlack(customers);
        this.customers = customers;
    }

    private void validateBlack(List<Customer> customers) {
        boolean existNonBlack = customers.stream().anyMatch(c -> !c.getCustomerType().isBlack());
        if (existNonBlack) {
            throw new IllegalArgumentException("고객 리스트에 블랙되지 않은 고객이 존재합니다.");
        }
    }

    public void forEach(Consumer<Customer> action) {
        for (Customer customer : customers) {
            action.accept(customer);
        }
    }

    public int size() {
        return customers.size();
    }

    public static BlackCustomers empty(){
        return new BlackCustomers(new ArrayList<>());
    }

    public static BlackCustomers valueOf(List<Customer> customers){
        return new BlackCustomers(customers);
    }

}
