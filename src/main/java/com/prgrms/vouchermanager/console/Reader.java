package com.prgrms.vouchermanager.console;

import com.prgrms.vouchermanager.exception.FileIOException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

    File file = new File("src/main/resources/customer_blacklist.csv");
    public Scanner fc;

    {
        try {
            fc = new Scanner(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileIOException();
        }
    }
}
