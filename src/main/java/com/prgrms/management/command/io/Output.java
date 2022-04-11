package com.prgrms.management.command.io;

import org.springframework.stereotype.Component;

@Component
public class Output {
    public void print(String output) {
        if(output.isEmpty()) System.out.println("리스트가 비어 있습니다.");
        else System.out.println(output);
    }
}
