package com.prgrms.vouchermanager.console;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Component
public class Reader {

    Scanner sc = new Scanner(System.in);

//    @Value("${csv.path}")
//    private String path;

    File file = new File("src\\main\\resources\\customer_blacklist.csv");

    public BufferedReader bf = new BufferedReader(new FileReader(file));

    public Reader() throws IOException {
    }
}
