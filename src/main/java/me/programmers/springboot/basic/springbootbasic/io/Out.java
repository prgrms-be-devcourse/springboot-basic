package me.programmers.springboot.basic.springbootbasic.io;

import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;

import java.util.List;

public class Out implements ConsoleOutput {

    @Override
    public void output(String message) {
        System.out.println(message);
    }

    @Override
    public void output(List<Customer> list) {
        list.forEach(customer -> {
            System.out.println("Customer Id: " + customer.getCustomerId());
            System.out.println("Customer name: " + customer.getName());
            System.out.println("Customer email: " + customer.getEmail());
            System.out.println();
        });
    }
}
