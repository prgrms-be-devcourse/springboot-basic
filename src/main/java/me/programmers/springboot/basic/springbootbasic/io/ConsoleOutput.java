package me.programmers.springboot.basic.springbootbasic.io;

import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;

import java.util.List;

public interface ConsoleOutput {

    void output(String s);
    void output(List<Customer> list);

}
