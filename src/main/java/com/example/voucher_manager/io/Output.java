package com.example.voucher_manager.io;

import java.util.List;

public interface Output {
    void printError();
    void print(String content);
    void exit();
    <T> void printItems(List<T> items);
}
