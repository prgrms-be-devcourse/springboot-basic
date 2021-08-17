package com.prgrms.devkdtorder.cla;

import java.util.List;

public interface Output {

    void printAppStart();

    void printCommandError();

    void print(String prompt);

    void print(List<String> list);
}
