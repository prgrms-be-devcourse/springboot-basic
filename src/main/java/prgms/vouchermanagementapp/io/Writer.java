package prgms.vouchermanagementapp.io;

import java.util.List;

public class Writer {

    public void print(List<?> list) {
        list.forEach(System.out::println);
    }

    public void print(Object message) {
        System.out.println(message);
    }
}
