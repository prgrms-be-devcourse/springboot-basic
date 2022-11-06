package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.io.message.SystemMessage;

import java.util.List;

public class Writer {

    public void print(List<String> list) {
        list.forEach(System.out::println);
    }

    public void printExitMessage() {
        System.out.println(SystemMessage.EXIT.getMessage());
    }

    public void printCommandGuide() {
        print(CommandType.getMessages());
    }
}
