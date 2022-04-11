package com.example.voucher_manager.io;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutput implements Output{

    @Override
    public void printError() {
        System.out.println("Invalid InputError.");
    }

    @Override
    public void print(String content) {
        System.out.println(content);
    }

    @Override
    public void exit() {
        System.out.println("Exit Voucher Manager.");
    }

    @Override
    public <T> void printItems(List<T> items) {
        items.forEach(System.out::println);
    }
}