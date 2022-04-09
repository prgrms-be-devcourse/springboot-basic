package com.example.voucher_manager.io;

import java.util.List;

public class ConsoleOutput implements Output{
    @Override
    public void printError() {
        System.out.println("잘못된 입력입니다.");
    }

    @Override
    public void print(String content) {
        System.out.println(content);
    }

    @Override
    public void exit() {
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public <T> void printItems(List<T> items) {

    }
}