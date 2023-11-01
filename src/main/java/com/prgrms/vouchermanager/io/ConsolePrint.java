package com.prgrms.vouchermanager.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ConsolePrint {

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printList(List<?> list) {
        list.forEach(component -> {
            System.out.println(component);
            System.out.println("---------------");
        });
        System.out.println();
    }
}
