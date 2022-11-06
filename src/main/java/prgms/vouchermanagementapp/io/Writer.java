package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.io.message.SystemMessage;

import java.util.List;

public class Writer {

    public void print(List<?> list) {
        list.forEach(System.out::println);
    }

    public void print(Object message) {
        System.out.println(message);
    }

    public void printExitMessage() {
        System.out.println(SystemMessage.EXIT.getMessage());
    }
}
