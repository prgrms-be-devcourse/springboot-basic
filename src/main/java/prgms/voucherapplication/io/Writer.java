package prgms.voucherapplication.io;

import java.util.List;

public class Writer {

    public void printLine(List<?> list) {
        list.forEach(System.out::println);
    }
}
