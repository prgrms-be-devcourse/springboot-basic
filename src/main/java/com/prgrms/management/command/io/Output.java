package com.prgrms.management.command.io;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Output {
    private final String LIST_EMPTY ="리스트가 비어 있습니다.";
    public void printGuide(String output) {
        System.out.println(output);
    }

    public <T> void printList(List<T> list) {
        if(list.size()==0) System.out.println(LIST_EMPTY);
        else System.out.println(list);
    }
}
